package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.dto.ArticleAddDto;
import com.mydddyh.domain.dto.ArticleListDto;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.vo.ArticleAdminDetailVo;

/**
 * 文章表(Article)表服务接口
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult articleDetail(Integer id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(ArticleAddDto articleDto);

    ResponseResult pageListArticle(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult getArticle(Integer id);

    ResponseResult updateArticle(ArticleAdminDetailVo articleAdminDetailVo);

    ResponseResult removeArticle(String id);
}
