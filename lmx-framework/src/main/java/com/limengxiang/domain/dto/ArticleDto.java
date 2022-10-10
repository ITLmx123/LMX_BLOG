package com.limengxiang.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    @TableId
    private Long id;

    //访问量
    private Long viewCount;
}
