package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.User;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.service.AdminLoginService;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "后台登录")
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    @SystemLog(businessName = "后台用户登录")
    @ApiOperation(value = "后台用户登录")
    @ApiImplicitParam(name = "用户名和密码")
    public ResponseResult login(@RequestBody User user){
        if (!Strings.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!Strings.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        return adminLoginService.login(user);
    }

    @PostMapping("/user/logout")
    @SystemLog(businessName = "退出登录")
    @ApiOperation(value = "退出登录")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }

    @GetMapping("/getInfo")
    @SystemLog(businessName = "获取用户信息")
    @ApiOperation(value = "获取用户信息")
    public ResponseResult getInfo(){
        return adminLoginService.getInfo();
    }

    @GetMapping("/getRouters")
    @SystemLog(businessName = "获取动态路由")
    @ApiOperation(value = "获取动态路由")
    public ResponseResult getRouters(){
        return adminLoginService.getRouters();
    }
}
