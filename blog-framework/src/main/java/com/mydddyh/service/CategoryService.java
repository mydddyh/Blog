package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.CategoryListDto;
import com.mydddyh.domain.dto.CategoryStatusDto;
import com.mydddyh.domain.entity.Category;
import com.mydddyh.domain.vo.CategoryDetailVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 分类表(Category)表服务接口
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    void export(HttpServletResponse response);

    ResponseResult pageListCategory(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult addCategory(CategoryDetailVo categoryDetailVo);

    ResponseResult getCategory(Integer id);

    ResponseResult updateCategory(CategoryDetailVo categoryDetailVo);

    ResponseResult removeCategory(String id);

    ResponseResult changeStatus(CategoryStatusDto categoryStatusDto);
}

