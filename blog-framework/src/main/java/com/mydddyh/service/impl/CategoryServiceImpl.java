package com.mydddyh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.CategoryListDto;
import com.mydddyh.domain.dto.CategoryStatusDto;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.domain.entity.Category;
import com.mydddyh.domain.vo.CategoryDetailVo;
import com.mydddyh.domain.vo.CategoryExcelVo;
import com.mydddyh.domain.vo.CategoryVo;
import com.mydddyh.domain.vo.PageVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.CategoryMapper;
import com.mydddyh.service.ArticleService;
import com.mydddyh.service.CategoryService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.ServiceUtils;
import com.mydddyh.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 查询文件表, 找出所有状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<Article>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        // 获取文章的分类id并去重
        List<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toList());
        // 查询分类表, 找出正常状态的分类
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>();
        queryWrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> categories = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public void export(HttpServletResponse response) {
        // 参考 https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#web%E4%B8%AD%E7%9A%84%E5%86%99%E5%B9%B6%E4%B8%94%E5%A4%B1%E8%B4%A5%E7%9A%84%E6%97%B6%E5%80%99%E8%BF%94%E5%9B%9Ejson
        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader(SystemConstants.CATEGORY_EXPORT_EXCEL_FILE_NAME, response);
            // 获取需要导出的数据
            List<Category> categories = list();
            List<CategoryExcelVo> categoryExcelVos = BeanCopyUtils.copyBeanList(categories, CategoryExcelVo.class);
            // 写入到Excel中
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(SystemConstants.CATEGORY_EXPORT_EXCEL_SHEET_NAME)
                    .doWrite(categoryExcelVos);
        } catch (Exception e) {
            // 如果出现异常也要响应json
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.EXPORT_FILE_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
        return;
    }

    @Override
    public ResponseResult pageListCategory(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>();
        queryWrapper.like(StringUtils.hasText(categoryListDto.getName()), Category::getName, ServiceUtils.addWildcard(categoryListDto.getName()));
        queryWrapper.like(StringUtils.hasText(categoryListDto.getStatus()), Category::getStatus, ServiceUtils.addWildcard(categoryListDto.getStatus()));
        Page<Category> page = new Page<Category>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CategoryDetailVo> categoryDetailVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryDetailVo.class);
        PageVo pageVo = new PageVo(categoryDetailVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDetailVo categoryDetailVo) {
        categoryDetailVo.setId(null);
        Category category = BeanCopyUtils.copyBean(categoryDetailVo, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Integer id) {
        Category category = getById(id);
        CategoryDetailVo categoryDetailVo = BeanCopyUtils.copyBean(category, CategoryDetailVo.class);
        return ResponseResult.okResult(categoryDetailVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDetailVo categoryDetailVo) {
        Category category = BeanCopyUtils.copyBean(categoryDetailVo, Category.class);
        if (!updateById(category)) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeCategory(String id) {
        // 剔除多行删除
        Integer categoryId;
        try {
            categoryId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new SystemException(AppHttpCodeEnum.DELETE_MULTI_ROWS);
        }
        // 删除分类
        if (!removeById(categoryId)) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(CategoryStatusDto categoryStatusDto) {
        Category category = BeanCopyUtils.copyBean(categoryStatusDto, Category.class);
        if (!updateById(category)) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }
}

