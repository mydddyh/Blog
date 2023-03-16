package com.mydddyh.handler.security;

import com.alibaba.fastjson.JSON;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 进行统一的异常处理
        authException.printStackTrace();
        ResponseResult result = null;
        if (authException instanceof InternalAuthenticationServiceException) {
            // 账号错误
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), authException.getMessage());
        }
        else if(authException instanceof BadCredentialsException){
            // 密码错误
            result = ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_ERROR);
//            result = ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_ERROR.getCode(), authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            // header没带token
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            // 其他异常
            result = ResponseResult.errorResult(AppHttpCodeEnum.OTHER_SYSTEM_ERROR);
        }

        // 响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
        return;
    }
}
