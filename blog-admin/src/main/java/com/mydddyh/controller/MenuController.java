package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.MenuListDto;
import com.mydddyh.domain.vo.MenuDetailVo;
import com.mydddyh.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    @SystemLog(businessName = "查询菜单列表")
    @ApiOperation(value = "查询菜单列表")
    @ApiImplicitParam(name = "menuListDto", value = "需要查询匹配的菜单名和状态")
    public ResponseResult list(MenuListDto menuListDto) {
        return menuService.listMenu(menuListDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    @SystemLog(businessName = "新增菜单")
    @ApiOperation(value = "新增菜单")
    public ResponseResult addMenu(@RequestBody MenuDetailVo menuDetailVo){
        return menuService.addMenu(menuDetailVo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:menu:list')")
    @SystemLog(businessName = "根据id获取菜单")
    @ApiOperation(value = "根据id获取菜单")
    @ApiImplicitParam(name = "id", value = "需要获取的菜单id")
    public ResponseResult getMenu(@PathVariable Integer id) {
        return menuService.getMenu(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('system:menu:edit')")
    @SystemLog(businessName = "修改菜单")
    @ApiOperation(value = "修改菜单")
    @ApiImplicitParam(name = "menuDetailVo", value = "需要修改的菜单id和信息")
    public ResponseResult updateMenu(@RequestBody MenuDetailVo menuDetailVo) {
        return menuService.updateMenu(menuDetailVo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:menu:remove')")
    @SystemLog(businessName = "根据id删除菜单")
    @ApiOperation(value = "根据id删除菜单")
    @ApiImplicitParam(name = "id", value = "需要删除的菜单id")
    public ResponseResult removeMenu(@PathVariable Integer id) {
        return menuService.removeMenu(id);
    }

}
