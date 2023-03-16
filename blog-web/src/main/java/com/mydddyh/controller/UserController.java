package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.User;
import com.mydddyh.domain.vo.UserInfoVo;
import com.mydddyh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    @ApiOperation(value = "获取用户信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息")
    @ApiImplicitParam(name = "更新后的用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo userInfoVo){
        return userService.updateUserInfo(userInfoVo);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册新用户")
    @ApiOperation(value = "注册新用户")
    @ApiImplicitParam(name = "新用户的信息")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
