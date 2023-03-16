package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.UserAddDto;
import com.mydddyh.domain.dto.UserGetDto;
import com.mydddyh.domain.dto.UserStatusDto;
import com.mydddyh.domain.vo.UserUpdateVo;
import com.mydddyh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
@Api(tags = "后台用户")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:user:list')")
    @SystemLog(businessName = "分页查询用户列表")
    @ApiOperation(value = "分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "userStatusDto", value = "需要查询匹配的用户名和信息"),
    })
    public ResponseResult list(Integer pageNum, Integer pageSize, UserGetDto userGetDto) {
        return userService.pageListUser(pageNum, pageSize, userGetDto);
    }

    @PostMapping()
    @PreAuthorize("@ps.hasPermission('system:user:add')")
    @SystemLog(businessName = "新增用户")
    @ApiOperation(value = "新增用户")
    @ApiImplicitParam(name = "userAddVo", value = "新增用户的用户信息和角色列表")
    public ResponseResult addUser(@RequestBody UserAddDto userAddDto) {
        return userService.addUser(userAddDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    @SystemLog(businessName = "删除用户")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "id", value = "需要删除的用户id")
    public ResponseResult removeUser(@PathVariable String id) {
        return userService.removeUser(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:user:query')")
    @SystemLog(businessName = "获取用户")
    @ApiOperation(value = "获取用户")
    @ApiImplicitParam(name = "id", value = "需要获取的用户id")
    public ResponseResult getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    @SystemLog(businessName = "修改用户")
    @ApiOperation(value = "修改用户")
    @ApiImplicitParam(name = "userUpdateVo", value = "需要修改的用户信息和角色列表")
    public ResponseResult updateUser(@RequestBody UserUpdateVo userUpdateVo) {
        return userService.updateUser(userUpdateVo);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    @SystemLog(businessName = "改变用户状态")
    @ApiOperation(value = "改变用户状态")
    @ApiImplicitParam(name = "userStatusDto", value = "需要设置的用户状态")
    public ResponseResult changeStatus(@RequestBody UserStatusDto userStatusDto) {
        return userService.changeStatus(userStatusDto);
    }
}
