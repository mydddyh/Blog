package com.mydddyh.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.mapper.ArticleMapper;
import com.mydddyh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询正式文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        // 封装文章信息 {id: viewCount}
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        a -> a.getId().toString(),
                        a -> a.getViewCount().intValue()
                ));

        // 把散列存到redis中
        redisCache.setCacheMap(SystemConstants.VIEW_COUNT_REDIS_KEY, viewCountMap);
        return;
    }
}
