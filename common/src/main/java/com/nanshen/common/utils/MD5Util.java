package com.nanshen.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MD5Util {


    /**
     * 获取文件md5
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String fileMd5(MultipartFile multipartFile) throws IOException {
        return DigestUtils.md5Hex(multipartFile.getBytes());
    }

    /**
     * 获取文件md5
     * @param file
     * @return
     * @throws IOException
     */
    public static String fileMd5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }


    /**
     * 获取文件md5
     * @param path
     * @return
     * @throws IOException
     */
    public static String fileMd5(String path) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(path));
    }


    /**
     * 字符串md5加密
     * @param data
     * @return
     */
    public static String strMd5(String data){
        return DigestUtils.md5Hex(data);
    }



    public static String sha1(String str1){
        return DigestUtils.sha1Hex(str1);
    }
}
