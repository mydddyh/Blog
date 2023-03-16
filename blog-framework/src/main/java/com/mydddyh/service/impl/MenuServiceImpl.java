package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.MenuListDto;
import com.mydddyh.domain.entity.Menu;
import com.mydddyh.domain.vo.MenuDetailVo;
import com.mydddyh.domain.vo.MenuListVo;
import com.mydddyh.domain.vo.MenuVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.MenuMapper;
import com.mydddyh.service.MenuService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.SecurityUtils;
import com.mydddyh.utils.ServiceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        // 写死超级管理员拥有所有的权限
        if(SystemConstants.SUPER_ADMIN_USER_ID.equals(userId)){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_MENU_TYPE_FIELD, SystemConstants.BUTTON_MENU_TYPE_FIELD);
            wrapper.eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则xml查权限
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        // 获取所有menu
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        if(SecurityUtils.isSuperAdmin()){
            // 管理员可以获得所有Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            // 其他用户只能获得对应Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 根据menu的parentId, 从所有的menuVo中构建tree
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        List<MenuVo> menuTree = builderMenuTree(menuVos, SystemConstants.ROOT_PARENT_ID_TYPE_FIELD);
        return menuTree;
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos, Long parentId) {
        // 设置第一层菜单的子菜单children
        List<MenuVo> menuTree = menuVos.stream()
                .filter(m -> parentId.equals(m.getParentId()))
                .map(m -> m.setChildren(getChildren(m, menuVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    private List<MenuVo> getChildren(MenuVo menuVo, List<MenuVo> menuVos) {
        // 递归设置children
        List<MenuVo> children = menuVos.stream()
                .filter(m -> menuVo.getId().equals(m.getParentId()))
                .map(m -> m.setChildren(getChildren(m, menuVos)))
                .collect(Collectors.toList());
        return children;
    }

    @Override
    public ResponseResult listMenu(MenuListDto menuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.like(StringUtils.hasText(menuListDto.getMenuName()), Menu::getMenuName, ServiceUtils.addWildcard(menuListDto.getMenuName()));
        queryWrapper.like(StringUtils.hasText(menuListDto.getStatus()), Menu::getStatus, ServiceUtils.addWildcard(menuListDto.getStatus()));
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    @Override
    public ResponseResult addMenu(MenuDetailVo menuDetailVo) {
        menuDetailVo.setId(null);
        Menu menu = BeanCopyUtils.copyBean(menuDetailVo, Menu.class);
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Integer id) {
        Menu menu = getById(id);
        MenuDetailVo menuDetailVo = BeanCopyUtils.copyBean(menu, MenuDetailVo.class);
        return ResponseResult.okResult(menuDetailVo);
    }

    @Override
    public ResponseResult updateMenu(MenuDetailVo menuDetailVo) {
        if (menuDetailVo.getParentId().equals(menuDetailVo.getId())) {
            throw new SystemException(AppHttpCodeEnum.MENU_ID_ERROR);
        }
        Menu menu = BeanCopyUtils.copyBean(menuDetailVo, Menu.class);
        if (!updateById(menu)) {
            throw new SystemException(AppHttpCodeEnum.MENU_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeMenu(Integer id) {
        // 有子菜单的不能删
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.eq(Menu::getParentId, id);
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.MENU_CHILDREN_EXIST);
        }
        if (!removeById(id)) {
            throw new SystemException(AppHttpCodeEnum.MENU_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }
}

