package com.mydddyh.handler.exception;

import com.mydddyh.domain.ResponseResult;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        log.error("出现了异常! ",e);
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseResult exceptionHandler(Exception e){
//        log.error("出现了异常! ",e);
//        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
//    }
}
