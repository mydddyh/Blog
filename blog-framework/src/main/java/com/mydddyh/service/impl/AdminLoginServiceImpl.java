package com.mydddyh.service.impl;

import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.LoginUser;
import com.mydddyh.domain.entity.User;
import com.mydddyh.domain.vo.AdminLoginUserInfoVo;
import com.mydddyh.domain.vo.MenuVo;
import com.mydddyh.domain.vo.RoutersVo;
import com.mydddyh.domain.vo.UserInfoVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.service.AdminLoginService;
import com.mydddyh.service.MenuService;
import com.mydddyh.service.RoleService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.JwtUtil;
import com.mydddyh.utils.RedisCache;
import com.mydddyh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
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
        redisCache.setCacheObject(SystemConstants.ADMIN_USERID_REDIS_PREFIX + userId, loginUser);

        // 只返回token
        Map<String, String> map = new HashMap<String, String>();
        map.put(SystemConstants.HEADER_TOKEN_FIELD, jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        //删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.ADMIN_USERID_REDIS_PREFIX + userId);
        return ResponseResult.okResult();
    }

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult getInfo() {
        // 获取当前用户
        User user = SecurityUtils.getLoginUser().getUser();
        Long id = user.getId();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(id);
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(id);

        //获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminLoginUserInfoVo adminLoginUserInfoVo = new AdminLoginUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminLoginUserInfoVo);
    }

    @Override
    public ResponseResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询menu, 以tree形式返回
        List<MenuVo> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }
}
