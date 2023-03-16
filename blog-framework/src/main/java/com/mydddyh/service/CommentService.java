package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

