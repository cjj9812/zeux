package com.nanshen.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.nanshen.common.enums.IEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpResponseUtil {

    public static void response(IEnum baseEnum, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter= null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", baseEnum.getMsg());
        jsonObject.put("code", baseEnum.getCode());
        jsonObject.put("data",null);
          //拦截器中返回前端页面信息的编码需要设置
        printWriter.append(jsonObject.toString());
        printWriter.close();
    }

}
