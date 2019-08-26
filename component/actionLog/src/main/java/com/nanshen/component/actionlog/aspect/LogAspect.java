package com.nanshen.component.actionlog.aspect;


import com.nanshen.common.utils.HttpServletUtil;
import com.nanshen.component.actionlog.annotation.Log;
import com.nanshen.component.actionlog.enums.LogType;
import com.nanshen.component.jwt.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.nanshen.component.actionlog.annotation.Log)")
    public void operationLog() {
    }

    /**
     * 返回通知
     * @param point
     * @param returnValue
     */
    @AfterReturning(returning = "returnValue", pointcut = "operationLog() && @annotation(log)")
    public void doAfterReturning(JoinPoint point, Object returnValue, Log log) throws Exception {
        HttpServletRequest request=HttpServletUtil.getRequest();
        String token=request.getHeader("Authorization");
        //读取session中的用户
        if(token!=null){
            String userId= JwtUtil.getId(token);
            //请求的IP
            logger.info("用户：{}请求{}.{}结束",userId,point.getTarget().getClass().getName(),point.getSignature().getName());

        }
    }


    /**
     * 前置通知
     * @param point
     * @param log
     */
    @Before("operationLog() && @annotation(log)")
    public void  userLogBeforeAdvice(JoinPoint point, Log log){
        HttpServletRequest request=HttpServletUtil.getRequest();
        String token=request.getHeader("Authorization");
        //读取session中的用户
        if(token!=null){
            String userId= JwtUtil.getId(token);
            //请求的IP
            logger.info("用户：{}请求{}.{}开始",userId,point.getTarget().getClass().getName(),point.getSignature().getName());

        }

    }




}
