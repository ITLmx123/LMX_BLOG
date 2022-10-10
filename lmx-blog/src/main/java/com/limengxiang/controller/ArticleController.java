package com.limengxiang.controller;

import com.limengxiang.annotation.SystemLog;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /**
     * 获取热门文章列表
     *
     * @return {@link ResponseResult}
     */
    @SystemLog(businessName = "获取热门文章列表")
    @GetMapping("/hotArticleList")
    public ResponseResult getHotArticleList() {
        return articleService.hotArticleList();
    }

    /**
     * 获取文章列表
     *
     * @param pageNum    page num
     * @param pageSize   page size
     * @param categoryId category id
     * @return {@link ResponseResult}
     */
    @SystemLog(businessName = "获取文章列表")
    @GetMapping("/articleList")
    public ResponseResult getArticleList(Integer pageNum,Integer pageSize,Long categoryId) {
        return articleService.getArticleList(pageNum,pageSize,categoryId);
    }

    @SystemLog(businessName = "根据ID获取文章")
    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable Long id){
        return articleService.getArticleById(id);
    }

    @SystemLog(businessName = "更新阅读浏览量")
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
    }
}
