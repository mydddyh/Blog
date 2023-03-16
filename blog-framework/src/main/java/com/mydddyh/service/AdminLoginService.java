package com.mydddyh.service;

import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult getInfo();

    ResponseResult getRouters();
}
