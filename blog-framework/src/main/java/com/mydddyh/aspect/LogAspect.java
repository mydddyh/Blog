package com.mydddyh.aspect;

import com.alibaba.fastjson.JSON;
import com.mydddyh.annotation.SystemLog;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("@annotation(com.mydddyh.annotation.SystemLog)")  // 指定切点
    public void pointCut() {
    }

    private ProceedingJoinPoint joinPoint;

    @Around("pointCut()")   // 环绕控制, 功能全面
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取被注解的方法对象
        this.joinPoint = joinPoint;

        Object ret;
        try {
            handleBefore();
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private String responseResult;

    private void handleBefore() {
        // 获取此次http请求
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 获取当前执行的被增强方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法上的SystemLog注解对象
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        // 转换默认参数
        String requestArgs = Strings.hasText(systemLog.requestArgs())? systemLog.requestArgs(): JSON.toJSONString(joinPoint.getArgs());
        this.responseResult = Strings.hasText(systemLog.responseResult())? systemLog.responseResult(): "";

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", requestArgs);
        return;
    }

    private void handleAfter(Object ret) {
        // 打印出参
        responseResult = Strings.hasText(responseResult)? responseResult: JSON.toJSONString(ret);
        log.info("Response       : {}", responseResult);
        return;
    }
}
