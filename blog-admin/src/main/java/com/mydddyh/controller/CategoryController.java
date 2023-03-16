package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.CategoryListDto;
import com.mydddyh.domain.dto.CategoryStatusDto;
import com.mydddyh.domain.vo.CategoryDetailVo;
import com.mydddyh.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/content/category")
@Api(tags = "文章分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "获取所有分类列表")
    @ApiOperation(value = "获取所有分类列表")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @SystemLog(businessName = "获取分类导出的Excel", requestArgs = "需要导出分类")
    @ApiOperation(value = "获取分类导出的Excel")
    public void export(HttpServletResponse response){
        categoryService.export(response);
        return;
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "分页查询菜单列表")
    @ApiOperation(value = "分页查询菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "categoryListDto", value = "需要查询匹配的分类名称和状态"),
    })
    public ResponseResult pageListCategory(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        return categoryService.pageListCategory(pageNum, pageSize, categoryListDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "新增分类")
    @ApiOperation(value = "新增分类")
    @ApiImplicitParam(name = "categoryDetailVo", value = "需要修改的分类id和信息")
    public ResponseResult addCategory(@RequestBody CategoryDetailVo categoryDetailVo){
        return categoryService.addCategory(categoryDetailVo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "根据id获取分类")
    @ApiOperation(value = "根据id获取分类")
    @ApiImplicitParam(name = "id", value = "需要获取的分类id")
    public ResponseResult getCategory(@PathVariable Integer id) {
        return categoryService.getCategory(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "修改分类")
    @ApiOperation(value = "修改分类")
    @ApiImplicitParam(name = "categoryDetailVo", value = "需要修改的分类id和信息")
    public ResponseResult updateCategory(@RequestBody CategoryDetailVo categoryDetailVo) {
        return categoryService.updateCategory(categoryDetailVo);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "改变分类状态")
    @ApiOperation(value = "改变分类状态")
    @ApiImplicitParam(name = "categoryStatusDto", value = "需要设置的分类状态")
    public ResponseResult changeStatus(@RequestBody CategoryStatusDto categoryStatusDto) {
        return categoryService.changeStatus(categoryStatusDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    @SystemLog(businessName = "根据id删除分类")
    @ApiOperation(value = "根据id删除分类")
    @ApiImplicitParam(name = "id", value = "需要删除的分类id")
    public ResponseResult removeCategory(@PathVariable String id) {
        return categoryService.removeCategory(id);
    }
}
