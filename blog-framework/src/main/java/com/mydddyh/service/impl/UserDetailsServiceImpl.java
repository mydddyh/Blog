package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.entity.LoginUser;
import com.mydddyh.domain.entity.User;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.mapper.MenuMapper;
import com.mydddyh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据userName查用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        // 判断是否查到用户
        if (Objects.isNull(user)) {
            throw new RuntimeException(AppHttpCodeEnum.USER_NOT_EXIST.getMsg());
        }
        // 授予后台用户权限
        if(SystemConstants.ADMIN_USER_TYPE_FIELD.equals(user.getType())){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        // 前台用户不需要权限
        return new LoginUser(user, null);
    }
}
