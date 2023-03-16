package com.mydddyh.service.impl;

import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.LoginUser;
import com.mydddyh.domain.entity.User;
import com.mydddyh.domain.vo.UserInfoVo;
import com.mydddyh.domain.vo.UserLoginVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.service.WebLoginService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.JwtUtil;
import com.mydddyh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WebLoginServiceImpl implements WebLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        authentication = authenticationManager.authenticate(authentication);

        // 判断用户是否通过验证, 实际无法到达这
        if (Objects.isNull(authentication)) {
            throw new RuntimeException(AppHttpCodeEnum.PASSWORD_ERROR.getMsg());
        }

        // 根据UserId生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 根据userId把loginUser放进Redis中
        redisCache.setCacheObject(SystemConstants.WEB_USERID_REDIS_PREFIX + userId, loginUser);

        // 封装token和用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        //删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.WEB_USERID_REDIS_PREFIX + userId);
        return ResponseResult.okResult();
    }
}
