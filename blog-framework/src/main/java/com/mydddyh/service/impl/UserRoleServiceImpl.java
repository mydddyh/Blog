package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.domain.entity.UserRole;
import com.mydddyh.mapper.UserRoleMapper;
import com.mydddyh.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

