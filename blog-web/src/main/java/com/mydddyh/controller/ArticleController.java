package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Api(tags = "前台文章")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
//    @SystemLog(businessName = "获取热门文章列表")
    @ApiOperation(value = "获取热门文章列表")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章分页列表")
    @ApiOperation(value = "获取文章分页列表", notes = "按页获取某种类型或者所有文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "categoryId", value = "文章类型(可选)", required = false),
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id获取文章")
    @ApiOperation(value = "根据id获取文章")
    @ApiImplicitParam(name = "id", value = "文章id")
    public ResponseResult articleDetail(@PathVariable Integer id) {
        return articleService.articleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
//    @SystemLog(businessName = "更新文章浏览量")
    @ApiOperation(value = "更新文章浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id"),
    })
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
