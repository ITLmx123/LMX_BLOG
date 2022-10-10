package com.limengxiang.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.limengxiang.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 分类表(LmxCategory)表实体类
 *
 * @author makejava
 * @since 2022-09-06 15:31:57
 */

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("lmx_category")
public class Category extends BaseEntity {
    @TableId
    private Long id;

    //分类名
    private String name;
    //父分类id，如果没有父分类为-1
    private Long pid;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;
}

