package com.nanshen.web.controller;


import com.google.zxing.WriterException;
import com.nanshen.common.controller.BaseController;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.component.fileupload.config.FileProperties;
import com.nanshen.component.image.qrcore.QRCoreProperties;
import com.nanshen.component.image.qrcore.QRCoreUtil;

import exceptions.QRDrawException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/pub/qrcore")
public class QRCoreController extends BaseController {

    @Autowired
    QRCoreUtil qrCoreUtil;

    @Autowired
    QRCoreProperties qrCoreProperties;

    @Autowired
    FileProperties fileProperties;




    @GetMapping("")
    public ReMessage qrcore(@RequestParam(name = "content") String content, HttpServletRequest request, HttpServletResponse response) throws QRDrawException {

        String qrName= UUID.randomUUID().toString().replace("-","")+".png";
        String qrPath="/usr/local/mdsoftware/wwwroot/JiCaiZhongBao/files/qrcore/"+qrName;
        File file=new File(qrCoreProperties.getSavePath());
        if(file.exists()==false) file.mkdirs();
        qrCoreUtil.createQRCode(content,qrPath,300,300);
        String url=fileProperties.getIp()+qrPath.replace(fileProperties.getDefaultDir(),"");
        return success(url);
    }
}
