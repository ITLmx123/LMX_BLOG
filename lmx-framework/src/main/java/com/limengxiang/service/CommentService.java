package com.limengxiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Comment;

public interface CommentService extends IService<Comment> {
    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
