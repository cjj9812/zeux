package com.nanshen.component.fileupload.utils;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class DownloadUtil {
    public void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String returnName) throws IOException {
        response.setContentType("application/octet-stream");
        returnName = response.encodeURL(new String(returnName.getBytes(),"ISO8859-1"));
        System.out.println(returnName);//保存的文件名,必须和页面编码一致,否则乱码
        response.addHeader("content-disposition","attachment;filename=" + returnName);
//        response.setCharacterEncoding("UTF-8");
        response.setContentLength(byteArrayOutputStream.size());
        ServletOutputStream outputstream = response.getOutputStream();	//取得输出流
        byteArrayOutputStream.writeTo(outputstream);					//写到输出流
        byteArrayOutputStream.close();									//关闭
        outputstream.flush();											//刷数据
    }

    public boolean downLoad(String fileName,String filePath,HttpServletResponse response) throws UnsupportedEncodingException {
        if (fileName != null) {
            //设置文件路径
            File file = new File(filePath);
            if (file.exists()) {
                fileName=response.encodeURL(new String(fileName.getBytes(),"ISO8859-1"));
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
}

