package com.mydddyh.service;

import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.User;

public interface WebLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
