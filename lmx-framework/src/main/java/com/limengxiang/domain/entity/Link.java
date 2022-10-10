package com.limengxiang.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.limengxiang.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2022-09-07 10:27:23
 */

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("lmx_link")
public class Link extends BaseEntity {
    @TableId
    private Long id;

    
    private String name;
    
    private String logo;
    
    private String description;
    //网站地址
    private String address;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
}

