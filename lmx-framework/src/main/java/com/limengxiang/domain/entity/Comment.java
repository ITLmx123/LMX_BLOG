package com.limengxiang.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.limengxiang.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("lmx_comment")
public class Comment extends BaseEntity {
    @TableId
    private Long id;
    //评论类型
    private String type;
    //文章ID
    private Long articleId;
    //根评论ID
    private Long rootId;
    //评论内容
    private String content;
    //所回复目标评论的ID
    private Long toCommentUserId;
    //回复目标评论ID
    private Long toCommentId;
}
