package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.UserGetDto;
import com.mydddyh.domain.dto.UserStatusDto;
import com.mydddyh.domain.entity.User;
import com.mydddyh.domain.dto.UserAddDto;
import com.mydddyh.domain.vo.UserInfoVo;
import com.mydddyh.domain.vo.UserUpdateVo;

/**
 * 用户表(User)表服务接口
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(UserInfoVo userInfoVo);

    ResponseResult register(User user);

    ResponseResult pageListUser(Integer pageNum, Integer pageSize, UserGetDto userGetDto);

    ResponseResult addUser(UserAddDto userAddDto);

    ResponseResult removeUser(String id);

    ResponseResult getUser(Integer id);

    ResponseResult updateUser(UserUpdateVo userUpdateVo);

    ResponseResult changeStatus(UserStatusDto userStatusDto);
}

