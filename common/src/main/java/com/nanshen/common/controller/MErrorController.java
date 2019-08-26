package com.nanshen.common.controller;

import com.nanshen.common.vo.ReMessage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
    public class MErrorController extends BasicErrorController {


        public MErrorController(){
            super(new DefaultErrorAttributes(), new ErrorProperties());
        }

        private static final String PATH = "/error";

        @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
        public ReMessage error1(HttpServletRequest request) {
            Map<String, Object> body = getErrorAttributes(request,
                    isIncludeStackTrace(request, MediaType.ALL));
            ReMessage reMessage=new ReMessage();
            HttpStatus status = getStatus(request);
            if (!Strings.isNotBlank((String)body.get("exception")) && body.get("exception").equals(AuthenticationException.class.getName())){
                reMessage.setCode(401);
                reMessage.setMessage("未认证");
                reMessage.setData(null);
            }
            return reMessage;
        }

        @Override
        public String getErrorPath() {
            return PATH;
        }
    }
