package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "文章分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
//    @SystemLog(businessName = "获取分类列表")
    @ApiOperation(value = "获取分类列表")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }
}
