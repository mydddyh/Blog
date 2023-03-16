package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.ArticleAddDto;
import com.mydddyh.domain.dto.ArticleListDto;
import com.mydddyh.domain.vo.ArticleAdminDetailVo;
import com.mydddyh.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
@Api(tags = "后台文章")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:article:list')")
    @SystemLog(businessName = "分页查询文章列表")
    @ApiOperation(value = "分页查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "articleListDto", value = "需要查询匹配的文章标题和摘要"),
    })
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        return articleService.pageListArticle(pageNum, pageSize, articleListDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @SystemLog(businessName = "新增文章")
    @ApiOperation(value = "新增文章")
    public ResponseResult addArticle(@RequestBody ArticleAddDto articleDto){
        return articleService.addArticle(articleDto);
    }


    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:article:list')")
    @SystemLog(businessName = "根据id获取文章")
    @ApiOperation(value = "根据id获取文章")
    @ApiImplicitParam(name = "id", value = "需要获取的文章id")
    public ResponseResult getArticle(@PathVariable Integer id) {
        return articleService.getArticle(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @SystemLog(businessName = "修改文章")
    @ApiOperation(value = "修改文章")
    @ApiImplicitParam(name = "articleAdminDetailVo", value = "需要修改的文章id和信息")
    public ResponseResult updateArticle(@RequestBody ArticleAdminDetailVo articleAdminDetailVo) {
        return articleService.updateArticle(articleAdminDetailVo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @SystemLog(businessName = "根据id删除文章")
    @ApiOperation(value = "根据id删除文章")
    @ApiImplicitParam(name = "id", value = "需要删除的文章id")
    public ResponseResult removeArticle(@PathVariable String id) {
        return articleService.removeArticle(id);
    }

}
