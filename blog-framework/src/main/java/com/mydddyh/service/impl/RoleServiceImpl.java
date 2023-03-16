package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.RoleListDto;
import com.mydddyh.domain.dto.RoleStatusDto;
import com.mydddyh.domain.entity.Menu;
import com.mydddyh.domain.entity.Role;
import com.mydddyh.domain.entity.RoleMenu;
import com.mydddyh.domain.vo.*;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.RoleMapper;
import com.mydddyh.service.MenuService;
import com.mydddyh.service.RoleMenuService;
import com.mydddyh.service.RoleService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        // 写死超级管理员拥有角色
        if(SystemConstants.SUPER_ADMIN_USER_ID.equals(userId)){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add(SystemConstants.SUPER_ADMIN_ROLE_KEY_TYPE_FIELD);
            return roleKeys;
        }
        // 否则xml查角色
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }

    @Override
    public ResponseResult pageListRole(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>();
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName, ServiceUtils.addWildcard(roleListDto.getRoleName()));
        queryWrapper.like(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus, ServiceUtils.addWildcard(roleListDto.getStatus()));
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<Role>(pageNum, pageSize);
        page(page, queryWrapper);
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleListVo.class);
        PageVo pageVo = new PageVo(roleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
        Role role = BeanCopyUtils.copyBean(roleStatusDto, Role.class);
        role.setId(roleStatusDto.getRoleId());  // 手动拷贝
        if (!updateById(role)) {
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    @Transactional
    public ResponseResult addRole(RoleDetailVo roleDetailVo) {
        // 新增role
        roleDetailVo.setId(null);
        Role role = BeanCopyUtils.copyBean(roleDetailVo, Role.class);
        save(role); // MP会添加插入后的id
        // 修改role-menu的关联表
        Long roleId = role.getId();
        List<Long> menuIds = roleDetailVo.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(roleId, menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeRole(String id) {
        // 剔除多行删除
        Integer roleId;
        try {
            roleId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new SystemException(AppHttpCodeEnum.DELETE_MULTI_ROWS);
        }
        // 删除角色
        if (!removeById(roleId)) {
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Integer id) {
        Role role = getById(id);
        RoleDetailVo roleDetailVo = BeanCopyUtils.copyBean(role, RoleDetailVo.class);
        return ResponseResult.okResult(roleDetailVo);
    }

    @Autowired
    private MenuService menuService;

    @Override
    public ResponseResult roleMenuTreeSelect(Integer id) {
        // 获取Menu构建的树
        List<MenuTreeVo> allMenuTreeVos = getAllMenus();
        List<MenuTreeVo> menuTree = getMenuTree(allMenuTreeVos);
        // 获取已经勾选的Menu
        List<Long> menuList;
        if (SystemConstants.SUPER_ADMIN_ROLE_ID.equals(id.longValue())) {
            // 超级管理员获得所有权限
            menuList = allMenuTreeVos.stream()
                    .map(MenuTreeVo::getId)
                    .collect(Collectors.toList());
        } else {
            LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<RoleMenu>();
            queryWrapper.eq(RoleMenu::getRoleId, id);
            List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
            menuList = roleMenus.stream()
                    .map(RoleMenu::getMenuId)
                    .collect(Collectors.toList());
        }
        // 封装
        RoleMenuVo roleMenuVo = new RoleMenuVo(menuTree, menuList);
        return ResponseResult.okResult(roleMenuVo);
    }

    @Override
    public ResponseResult treeSelect() {
        List<MenuTreeVo> allMenuTreeVos = getAllMenus();
        List<MenuTreeVo> menuTree = getMenuTree(allMenuTreeVos);
        return ResponseResult.okResult(menuTree);
    }

    private List<MenuTreeVo> getAllMenus() {
        // 找出所有可用的Menu
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = menuService.list(queryWrapper);
        List<MenuTreeVo> allMenuTreeVos = BeanCopyUtils.copyBeanList(menus, MenuTreeVo.class);
        // 单独拷贝下menuName->label字段
        for (int i=0; i<menus.size(); i++) {
            allMenuTreeVos.get(i).setLabel(menus.get(i).getMenuName());
        }
        return allMenuTreeVos;
    }

    private List<MenuTreeVo> getMenuTree(List<MenuTreeVo> allMenuTreeVos) {
        // 构建Children组成的树
        List<MenuTreeVo> menuTree = allMenuTreeVos.stream()
                .filter(m -> SystemConstants.ROOT_PARENT_ID_TYPE_FIELD.equals(m.getParentId()))
                .map(m -> m.setChildren(getChildren(m, allMenuTreeVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    private List<MenuTreeVo> getChildren(MenuTreeVo menu, List<MenuTreeVo> allMenus) {
        // 递归生成children
        List<MenuTreeVo> menuTreeVos = allMenus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, allMenus)))
                .collect(Collectors.toList());
        return menuTreeVos;
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleDetailVo roleDetailVo) {
        Long roleId = roleDetailVo.getId();
        if (Objects.isNull(roleId)) {
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }
        // 不能修改超级管理员
        if (SystemConstants.SUPER_ADMIN_ROLE_ID.equals(roleId)) {
            throw new SystemException(AppHttpCodeEnum.UPDATE_SUPER_ADMIN);
        }
        // 修改Role
        Role role = BeanCopyUtils.copyBean(roleDetailVo, Role.class);
        if (!updateById(role)) {
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }
        // 修改Role-Menu关联表
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<RoleMenu>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        roleMenuService.remove(queryWrapper);
        List<Long> menuIds = roleDetailVo.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(roleId, menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        List<RoleListVo> roleListVos = getAllStatusNormalRoles();
        return ResponseResult.okResult(roleListVos);
    }

    public List<RoleListVo> getAllStatusNormalRoles() {
        // 找出所有可用的Role
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>();
        queryWrapper.eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        queryWrapper.orderByAsc(Role::getRoleSort);
        List<Role> roles = list(queryWrapper);
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(roles, RoleListVo.class);
        return roleListVos;
    }
}

