package com.limengxiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Category;


/**
 * 分类表(LmxCategory)表服务接口
 *
 * @author makejava
 * @since 2022-09-06 15:31:58
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

