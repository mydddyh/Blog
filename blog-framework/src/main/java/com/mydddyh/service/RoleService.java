package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.RoleListDto;
import com.mydddyh.domain.dto.RoleStatusDto;
import com.mydddyh.domain.entity.Role;
import com.mydddyh.domain.vo.RoleDetailVo;
import com.mydddyh.domain.vo.RoleListVo;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult pageListRole(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    ResponseResult changeStatus(RoleStatusDto roleStatusDto);

    ResponseResult addRole(RoleDetailVo roleDetailVo);

    ResponseResult removeRole(String id);

    ResponseResult getRole(Integer id);

    ResponseResult roleMenuTreeSelect(Integer id);

    ResponseResult updateRole(RoleDetailVo roleDetailVo);

    ResponseResult treeSelect();

    ResponseResult listAllRole();

    List<RoleListVo> getAllStatusNormalRoles();

}

