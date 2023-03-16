package com.mydddyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mydddyh.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
