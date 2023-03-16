package com.mydddyh.job;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.service.ArticleService;
import com.mydddyh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

//    @Scheduled(cron = "0/5 * * * * ?")  // 每5秒
    @Scheduled(cron = "0 0/10 * * * ?")  // 每10分钟
    public void updateViewCount() {
        // 从Redis中年获取数据
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.VIEW_COUNT_REDIS_KEY);
        // 更新浏览量到数据库
        viewCountMap.entrySet()
                .stream()
                .forEach(e -> updateArticleViewCount(Long.valueOf(e.getKey()), e.getValue().longValue()));
        return;
    }

    private void updateArticleViewCount(Long id, Long viewCount) {
        // 根据id更新浏览量
        Article article = new Article();
        article.setId(id);
        article.setViewCount(viewCount);
        articleService.updateById(article);
        return;
    }
}
