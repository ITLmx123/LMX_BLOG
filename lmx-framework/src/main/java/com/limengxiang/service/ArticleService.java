package com.limengxiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult getArticleList(Integer pageNum,Integer pageSize,Long categoryId);

    ResponseResult getArticleById(Long id);

    ResponseResult updateViewCount(Long id);
}
