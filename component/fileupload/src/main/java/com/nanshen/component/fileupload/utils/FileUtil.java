package com.nanshen.component.fileupload.utils;

import com.nanshen.common.utils.MD5Util;
import com.nanshen.common.utils.TimeUtil;
import com.nanshen.component.fileupload.enums.FileEnum;
import com.nanshen.component.fileupload.exceptions.FileNotUploadException;
import com.nanshen.module.system.entity.SysFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {


    //单文件上传（小文件）
    public static SysFile uploadFile(MultipartFile multipartFile,String saveDir,String fileType) throws FileNotUploadException, IOException {
        //判断是否上传有效文件
        if(multipartFile==null||multipartFile.getSize()==0){
            throw new FileNotUploadException(FileEnum.NOT_UOLOAD);
        }
        SysFile sysFile=new SysFile();
        String originalName=multipartFile.getOriginalFilename();
        sysFile.setName(originalName.substring(0,originalName.lastIndexOf(".")));
        sysFile.setSuffix(originalName.substring(originalName.lastIndexOf(".")+1));
        sysFile.setSize(multipartFile.getSize());
        sysFile.setCreateTime(TimeUtil.now());
        sysFile.setIsDel("false");
        sysFile.setMd5(MD5Util.fileMd5(multipartFile));
        sysFile.setPath(saveDir+"/"+fileType+"/"+genDateMkdir("yyyyMMdd")+"/"+sysFile.getMd5()+"."+sysFile.getSuffix());
         return sysFile;
    }


    //
    public static void transferTo(MultipartFile multipartFile,SysFile sysFile) throws IOException {
        String savePath=sysFile.getPath();
        //文件保存目录
        String originalName="/"+sysFile.getMd5()+"."+sysFile.getSuffix();
        String saveDir=savePath.replace(originalName,"");
        //判断文件目录是否存在，不存在则创建。
        File dir=new File(saveDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File destFile=new File(dir.getAbsolutePath()+File.separator+originalName);
        destFile.createNewFile();
        multipartFile.transferTo(destFile);
    }



    //
    public static String genDateMkdir(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
}
