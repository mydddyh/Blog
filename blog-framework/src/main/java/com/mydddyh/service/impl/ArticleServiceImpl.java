package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.dto.ArticleAddDto;
import com.mydddyh.domain.dto.ArticleListDto;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.ArticleTag;
import com.mydddyh.domain.vo.*;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.ArticleMapper;
import com.mydddyh.service.ArticleService;
import com.mydddyh.service.ArticleTagService;
import com.mydddyh.service.CategoryService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.RedisCache;
import com.mydddyh.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章, 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询10条
        Page<Article> page = new Page<Article>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

//        List<HotArticleVo> articleVos = new ArrayList<HotArticleVo>();
//        for (Article article: articles) {
//            HotArticleVo articleVo = new HotArticleVo();
//            BeanUtils.copyProperties(article, articleVo);
//            articleVos.add(articleVo);
//        }

        List<ArticleHotVo> articleVos = BeanCopyUtils.copyBeanList(articles, ArticleHotVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 等效于 select a.id, a.title, a.content, a.category_id, c.name from tbl_article as a join tbl_category as c on a.category_id = c.id where a.del_flag=0 and a.status=0 and c.status=0;
        // 根据分类查询文章, 并分页
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // 如果有categoryId就要查询文章分类
        queryWrapper.eq(categoryId!=null && categoryId>0 ,Article::getCategoryId, categoryId);
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop排序实现置顶功能
        queryWrapper.orderByDesc(Article::getIsTop);
        //最多只查询10条
        Page<Article> page = new Page(pageNum,pageSize);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        // 查询categoryName
        articles.forEach(article -> {
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    setViewCount(article);
                });

        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleDetail(Integer id) {
        // 查询文章id
        Article article = getById(id);
        // 设置浏览量
        setViewCount(article);
        // 设置类型
        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        // 拷贝
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应文章的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.VIEW_COUNT_REDIS_KEY, id.toString(),1);
        return ResponseResult.okResult();
    }

    private void setViewCount(Article article) {
        // 从redis获取最新浏览量
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_REDIS_KEY, article.getId().toString());
        if (Objects.isNull(viewCount)) {
            // redis没查到说明是新文章
            viewCount = Math.toIntExact(article.getViewCount());
            redisCache.setCacheMapValue(SystemConstants.VIEW_COUNT_REDIS_KEY, article.getId().toString(), viewCount.toString());
        }
        article.setViewCount(viewCount.longValue());
        return;
    }

    @Autowired
    private ArticleTagService articleTagService;

    // 事务注解
    @Override
    @Transactional
    public ResponseResult addArticle(ArticleAddDto articleDto) {
        // 新增文章
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        // 根据列表生成tag
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        // 添加文章与标签的关联到关联表中
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageListArticle(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        // 分页查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, ServiceUtils.addWildcard(articleListDto.getTitle()));
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, ServiceUtils.addWildcard(articleListDto.getSummary()));
        Page<Article> page = new Page<Article>(pageNum, pageSize);
        page(page, queryWrapper);
        // 封装
        List<ArticleAdminListVo> articleAdminListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleAdminListVo.class);
        PageVo pageVo = new PageVo(articleAdminListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticle(Integer id) {
        Article article = getById(id);
        ArticleAdminDetailVo articleAdminDetailVo = BeanCopyUtils.copyBean(article, ArticleAdminDetailVo.class);
        // 填充tags
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<Long> tags = articleTagService.list(queryWrapper).stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        articleAdminDetailVo.setTags(tags);
        return ResponseResult.okResult(articleAdminDetailVo);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(ArticleAdminDetailVo articleAdminDetailVo) {
        // 更新文章表
        Long id = articleAdminDetailVo.getId();
        if (Objects.isNull(id)) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }
        articleAdminDetailVo.setCreateBy(null);
        articleAdminDetailVo.setCreateTime(null);
        articleAdminDetailVo.setStatus(SystemConstants.ARTICLE_STATUS_NORMAL);  // 更新时会自动将草稿发布
        Article article = BeanCopyUtils.copyBean(articleAdminDetailVo, Article.class);
        if (!updateById(article)) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }
        // 更新关联表, 由于MP不支持联合主键, 只能先删再存
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        articleTagService.remove(queryWrapper);
        List<ArticleTag> articleTags = articleAdminDetailVo.getTags().stream()
                .map(tagId -> new ArticleTag(id, tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult removeArticle(String id) {
        // 剔除多行删除
        Integer articleId;
        try {
            articleId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new SystemException(AppHttpCodeEnum.DELETE_MULTI_ROWS);
        }
        // 删除文章
        if (!removeById(articleId)) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }
        // 对文章逻辑删除, 完全可以保留关联表的记录
//        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>();
//        queryWrapper.eq(ArticleTag::getArticleId, articleId);
//        articleTagService.remove(queryWrapper);
        return ResponseResult.okResult();
    }
}
