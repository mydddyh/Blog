package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.Article;
import com.mydddyh.domain.entity.Comment;
import com.mydddyh.domain.entity.User;
import com.mydddyh.domain.vo.CommentVo;
import com.mydddyh.domain.vo.PageVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.CommentMapper;
import com.mydddyh.service.ArticleService;
import com.mydddyh.service.CommentService;
import com.mydddyh.service.UserService;
import com.mydddyh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT_TYPE_FIELD.equals(type), Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getType, type);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT_ROOT_ID_FIELD);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        // 分页查询
        Page<Comment> page = new Page<Comment>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Comment> comments = page.getRecords();
        // 封装vo
        List<CommentVo> commentVos = toCommentVoList(comments);
        // 查询每个根评论的子评论
        commentVos.stream().forEach(c -> c.setChildren(getChildren(c.getId())));
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult addComment(Comment comment) {
        Article article = articleService.getById(comment.getArticleId());
        if (SystemConstants.ARTICLE_NO_COMMENT_IS_COMMENT_TYPE_FIELD.equals(article.getIsComment())) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NO_COMMENT);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        // 根据评论的id填充子评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }


    private List<CommentVo> toCommentVoList(List<Comment> comments){
        // 根据userId用nickName填充两个UserName
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class)
            .stream()
            .peek(this::setUserInfo)
            .collect(Collectors.toList());
        return commentVos;
    }

    private void setUserInfo(CommentVo c) {
        // 填充昵称和头像
        User user = userService.getById(c.getCreateBy());
        c.setUserName(user.getNickName());  // 填充昵称而不是用户名
        c.setAvatar(user.getAvatar());
        if (!SystemConstants.ROOT_COMMENT_TO_COMMENT_USER_ID_FIELD.equals(c.getToCommentUserId()))
            // 子评论还要被评论的userName
            c.setToCommentUserName(userService.getById(c.getToCommentUserId()).getNickName());
        return;
    }
}

