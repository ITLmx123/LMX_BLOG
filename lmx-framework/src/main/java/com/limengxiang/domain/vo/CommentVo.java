package com.limengxiang.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private Long id;
    //文章ID
    private Long articleId;
    //根评论ID
    private Long rootId;
    //评论内容
    private String content;
    //所回复目标评论的ID
    private Long toCommentUserId;

    private String toCommentUserName;
    //回复目标评论ID
    private Long toCommentId;

    private String username;

    private String avatar;

    private List<CommentVo> children;

    private Long createBy;

    private Date createTime;
}
