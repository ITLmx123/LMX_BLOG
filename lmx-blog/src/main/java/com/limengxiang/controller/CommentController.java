package com.limengxiang.controller;

import com.limengxiang.annotation.SystemLog;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Comment;
import com.limengxiang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @SystemLog(businessName = "获取文章评论列表")
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId ,Integer pageNum ,Integer pageSize){
        return commentService.commentList(SystemConstant.ARTICLE_COMMENT_TYPE,articleId,pageNum,pageSize);
    }

    @SystemLog(businessName = "获取友链评论列表")
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum ,Integer pageSize){
        return commentService.commentList(SystemConstant.LINK_COMMENT_TYPE,null,pageNum,pageSize);
    }

    @SystemLog(businessName = "添加评论")
    @PostMapping()
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
