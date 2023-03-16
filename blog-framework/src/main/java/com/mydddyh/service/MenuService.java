package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.MenuListDto;
import com.mydddyh.domain.entity.Menu;
import com.mydddyh.domain.vo.MenuDetailVo;
import com.mydddyh.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listMenu(MenuListDto menuListDto);

    ResponseResult addMenu(MenuDetailVo menuDetailVo);

    ResponseResult getMenu(Integer id);

    ResponseResult updateMenu(MenuDetailVo menuDetailVo);

    ResponseResult removeMenu(Integer id);
}

