package com.nanshen.web.exceptions;

import com.nanshen.common.enums.SystemEnum;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.web.enums.LoginEnum;
import com.nanshen.web.enums.SysUserEnum;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandle {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ReMessage IncorrectCredentialsException(IncorrectCredentialsException ex){
        logger.error("异常具体信息："+ex.getMessage());
        ex.printStackTrace();
        return new ReMessage(LoginEnum.PWD_INCORRECT);
    }

    @ExceptionHandler(ExcessiveAttemptsException.class)
    public ReMessage ExcessiveAttemptsException(ExcessiveAttemptsException ex){
        logger.error("异常具体信息："+ex.getMessage());
        ex.printStackTrace();
        return new ReMessage(LoginEnum.USER_LOCK);
    }

    @ExceptionHandler(UnknownAccountException.class)
    public ReMessage UnknownAccountException(UnknownAccountException ex){
        logger.error("异常具体信息："+ex.getMessage());
        ex.printStackTrace();
        return new ReMessage(SysUserEnum.NOT_EXIST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ReMessage MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        logger.error("异常名："+ex.getName()+" 具体信息："+ex.getMessage());
        ex.printStackTrace();
        return new ReMessage(SystemEnum.ERROR,"参数类型不匹配");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReMessage MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.error(" 具体信息："+ex.getMessage());
        ex.printStackTrace();
        BindingResult bindingResult=ex.getBindingResult();
        List<ObjectError> errorList= bindingResult.getAllErrors();
        String message=errorList.get(0).getDefaultMessage();
        return new ReMessage(SystemEnum.PARAM_INCORRECT.getCode(),message);
    }

    @ExceptionHandler(Exception.class)
    public ReMessage Exception(Exception ex){
        logger.error("异常具体信息："+ex.getMessage());
        ex.printStackTrace();
        return new ReMessage(SystemEnum.ERROR,ex.getMessage());
    }




}
