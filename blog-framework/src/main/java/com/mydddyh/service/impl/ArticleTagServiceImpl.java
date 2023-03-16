package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.domain.entity.ArticleTag;
import com.mydddyh.mapper.ArticleTagMapper;
import com.mydddyh.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

