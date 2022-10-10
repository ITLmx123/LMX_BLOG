package com.limengxiang.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;
    //标题
    private String title;

    //分类名
    private String categoryName;

    private Date createTime;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;
}
