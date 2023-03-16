package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.Comment;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.service.CommentService;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "根据文章id获取评论分页列表")
    @ApiOperation(value = "根据文章id获取评论分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id"),
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
    })
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT_TYPE_FIELD, articleId, pageNum, pageSize);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "获取友链评论列表")
    @ApiOperation(value = "获取友链评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT_TYPE_FIELD, null, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "新增评论")
    @ApiOperation(value = "新增评论")
    @ApiImplicitParam(name = "comment", value = "评论内容和相关信息")
    public ResponseResult addComment(@RequestBody Comment comment) {
        // 过滤空评论
        if (!Strings.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_COMMENT);
        }
        return commentService.addComment(comment);
    }
}
