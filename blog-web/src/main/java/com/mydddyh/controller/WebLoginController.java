package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.User;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.service.WebLoginService;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "前台登录")
public class WebLoginController {
    @Autowired
    private WebLoginService webLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "前台用户登录")
    @ApiOperation(value = "前台用户登录")
    @ApiImplicitParam(name = "用户名和密码")
    public ResponseResult login(@RequestBody User user){
        if (!Strings.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!Strings.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        return webLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "退出登录")
    @ApiOperation(value = "退出登录")
    public ResponseResult logout(){
        return webLoginService.logout();
    }
}
