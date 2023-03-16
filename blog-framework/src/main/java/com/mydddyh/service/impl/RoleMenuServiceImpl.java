package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.domain.entity.RoleMenu;
import com.mydddyh.mapper.RoleMenuMapper;
import com.mydddyh.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

