package com.mydddyh.service.impl;

import com.mydddyh.service.PermissionService;
import com.mydddyh.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionServiceImpl implements PermissionService {
    @Override
    public Boolean hasPermission(String permission) {
        // 超级管理员拥有所有权限
        if(SecurityUtils.isSuperAdmin()){
            return true;
        }
        // 根据当前登录用户的权限列表判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
