package com.nanshen.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanshen.common.controller.BaseController;
import com.nanshen.common.enums.SystemEnum;
import com.nanshen.common.exceptions.SelfException;
import com.nanshen.common.utils.MD5Util;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.component.actionlog.annotation.Log;
import com.nanshen.component.fileupload.config.FileProperties;
import com.nanshen.component.fileupload.enums.FileEnum;
import com.nanshen.component.fileupload.exceptions.FileNotUploadException;
import com.nanshen.component.fileupload.utils.FileUtil;
import com.nanshen.module.system.entity.SysFile;
import com.nanshen.module.system.service.SysFileService;
import com.nanshen.module.system.vo.SysFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pub")
public class UploadController extends BaseController {

    @Autowired
    FileProperties fileProperties;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    SysFileService sysFileService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    List<String> imgTypes= Arrays.asList("image/gif","image/jpg","image/jpeg","image/png");

    List<String> docTypes= Arrays.asList("application/pdf","application/msword");


    /**
     * 上传图片接口（单文件）
     * @param multipartFile
     * @return
     * @throws IOException
     * @throws FileNotUploadException
     */
    @PostMapping("/upload/img")
    public ReMessage uploadImg(@RequestParam(name = "img") MultipartFile multipartFile) throws IOException, FileNotUploadException {
        if(!imgTypes.contains(multipartFile.getContentType())){
            return error(FileEnum.NOT_SUPPORT_TYPE);
        }
        if(multipartFile.getSize()>1024*10000){
            return error(FileEnum.NOT_SUPPORT_SIZE);
        }
        SysFile sysFile=saveFile(multipartFile,"images");
        SysFileVO sysFileVO=new SysFileVO(sysFile);
        String savePath=sysFileVO.getPath();
        //拼接访问url
        sysFileVO.setServerUrl("//"+fileProperties.getIp()+savePath.replace(fileProperties.getDefaultDir(),""));
        return success(sysFileVO);
    }


    /**
     * 上传文档接口（单文件）
     * @param multipartFile
     * @return
     * @throws IOException
     * @throws FileNotUploadException
     */
    @PostMapping("/upload/doc")
    public ReMessage uploadDoc(@RequestParam(name = "doc") MultipartFile multipartFile) throws IOException, FileNotUploadException {
        if(!docTypes.contains(multipartFile.getContentType())){
            return error(FileEnum.NOT_SUPPORT_TYPE);
        }
        SysFile sysFile=saveFile(multipartFile,"doc");
        return success(sysFile);
    }


    /**
     * 保存文件到服务器并保存信息到数据库
     * @param multipartFile
     * @param fileType
     * @return
     * @throws IOException
     * @throws FileNotUploadException
     */
    public SysFile saveFile(MultipartFile multipartFile,String fileType) throws IOException, FileNotUploadException {
        SysFile sysFile=FileUtil.uploadFile(multipartFile,fileProperties.getDefaultDir(),fileType);
        SysFile existFile=sysFileService.findByMd5(sysFile.getMd5());
        if(existFile!=null){
            return existFile;
        }
        FileUtil.transferTo(multipartFile,sysFile);
        sysFileService.create(sysFile);
        return sysFile;
    }


    /**
     *
     * @param mediaId
     * @param accessToken
     * @return
     * @throws SelfException
     * @throws IOException
     */
    @Log(type = "base")
    @GetMapping("/wx/img")
    public ReMessage wxUpLoad(@RequestParam("mediaId") String mediaId,
                              @RequestParam("accessToken") String accessToken) throws SelfException, IOException {

        //1、从微信获取文件流
        Map<String,Object> map = getInputStream(accessToken, mediaId);
        if(map==null) return error();
        logger.info("获取文件流成功");
        //2、将文件流保存到本地服务器
        SysFile file=saveFile((InputStream)map.get("inputStream"),mediaId, (String) map.get("fileType"));
        logger.info("下载文件成功");
        SysFileVO sysFileVO=new SysFileVO(file);
        //拼接访问url
        sysFileVO.setServerUrl("//"+fileProperties.getIp()+"/wx/"+file.getMd5()+"."+file.getSuffix());
        return success(sysFileVO);

    }

    private SysFile saveFile(InputStream inputStream, String mediaId,String fileType) {
        byte[] data = new byte[10240];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        String saveDir=fileProperties.getDefaultDir()+"/wx/";
        File parentFile=new File(saveDir);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        String realPath=saveDir+mediaId+"."+fileType.toLowerCase();
        File saveFile=new File(realPath);
        try {
            fileOutputStream = new FileOutputStream(saveFile);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File file=new File(realPath);
        SysFile sysFile=new SysFile();
        sysFile.setMd5(mediaId);
        SysFile existFile=sysFileService.findByMd5(mediaId);
        if(existFile!=null) return existFile;
        sysFile.setSuffix(fileType.toLowerCase());
        sysFile.setName(mediaId);
        sysFile.setCreateTime(System.currentTimeMillis());
        sysFile.setPath(fileProperties.getDefaultDir()+"/wx/"+mediaId+"."+fileType);
        sysFile.setSize(file.length());
        sysFile.setIsDel("false");
        sysFileService.create(sysFile);
        return sysFile;
    }

    private Map<String,Object> getInputStream(String accessToken, String mediaId) throws  IOException {
        try {
            String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
                    + accessToken + "&media_id=" + mediaId;
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            String content_type = conn.getHeaderField("content-type");
            String fileType=null;
            if (content_type==null){
                return null;
            }else if(content_type.equals("image/jpg")) {
                fileType="jpg";
            }else if(content_type.equals("image/jpeg")) {
                fileType="jpeg";
            }else if( content_type.equals("image/png")) {
                fileType="png";
            }
            //微信服务器生成的文件名称
            //生成不同文件名称
            Map<String,Object> map=new HashMap<>();
            map.put("inputStream",inputStream);
            map.put("fileType",fileType);
            return map;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

}
