package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.RoleListDto;
import com.mydddyh.domain.dto.RoleStatusDto;
import com.mydddyh.domain.vo.RoleDetailVo;
import com.mydddyh.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
@Api(tags = "角色")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:role:list')")
    @SystemLog(businessName = "分页查询角色列表")
    @ApiOperation(value = "分页查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "roleListDto", value = "需要查询匹配的角色名称和状态"),
    })
    public ResponseResult list(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        return roleService.pageListRole(pageNum, pageSize, roleListDto);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @SystemLog(businessName = "改变角色状态")
    @ApiOperation(value = "改变角色状态")
    @ApiImplicitParam(name = "roleStatusDto", value = "需要设置的角色状态")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto) {
        return roleService.changeStatus(roleStatusDto);
    }

    @PostMapping()
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @SystemLog(businessName = "新增角色")
    @ApiOperation(value = "新增角色")
    @ApiImplicitParam(name = "roleDetailVo", value = "新增角色的角色信息和菜单列表")
    public ResponseResult addRole(@RequestBody RoleDetailVo roleDetailVo) {
        return roleService.addRole(roleDetailVo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    @SystemLog(businessName = "删除角色")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "id", value = "需要删除的角色id")
    public ResponseResult removeRole(@PathVariable String id) {
        return roleService.removeRole(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @SystemLog(businessName = "获取角色")
    @ApiOperation(value = "获取角色")
    @ApiImplicitParam(name = "id", value = "需要获取的角色id")
    public ResponseResult getRole(@PathVariable Integer id) {
        return roleService.getRole(id);
    }

    @GetMapping("/treeSelect")
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @SystemLog(businessName = "获取新角色拥有的默认菜单树")
    @ApiOperation(value = "获取新角色拥有的默认菜单树")
    public ResponseResult treeSelect() {
        return roleService.treeSelect();
    }

    @GetMapping("/roleMenuTreeSelect/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @SystemLog(businessName = "获取角色拥有的菜单树")
    @ApiOperation(value = "获取角色拥有的菜单树")
    @ApiImplicitParam(name = "id", value = "需要获取菜单树的角色id")
    public ResponseResult roleMenuTreeSelect(@PathVariable Integer id) {
        return roleService.roleMenuTreeSelect(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @SystemLog(businessName = "修改角色")
    @ApiOperation(value = "修改角色")
    @ApiImplicitParam(name = "roleDetailVo", value = "需要修改的角色信息和菜单列表")
    public ResponseResult updateRole(@RequestBody RoleDetailVo roleDetailVo) {
        return roleService.updateRole(roleDetailVo);
    }

    @GetMapping("/listAllRole")
    @PreAuthorize("@ps.hasPermission('system:role:list')")
    @SystemLog(businessName = "获取所有正常状态的角色列表")
    @ApiOperation(value = "获取所有正常状态的角色列表")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }
}
