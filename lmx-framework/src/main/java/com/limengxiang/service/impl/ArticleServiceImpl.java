package com.limengxiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.PageResult;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Article;
import com.limengxiang.domain.entity.Category;
import com.limengxiang.domain.vo.ArticleListVo;
import com.limengxiang.domain.vo.ArticleVo;
import com.limengxiang.domain.vo.HotArticleListVo;
import com.limengxiang.mapper.ArticleMapper;
import com.limengxiang.mapper.CategoryMapper;
import com.limengxiang.service.ArticleService;
import com.limengxiang.utils.BeanCopyUtil;
import com.limengxiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service()
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询热门文章列表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //条件1 状态(status)等于0
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        //条件2 是否删除(del_flag)等于0  配置文件有默认值不需要设置
        //根据浏览量降序排序(view-count)
        queryWrapper.orderByDesc(Article::getViewCount);
        //设置最多查询十条
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        //获取redis浏览量缓存并设置
//        articles.stream()
//                .map(article -> article.setViewCount(((Integer)redisCache.getCacheMapValue(SystemConstant.REDIS_VIEW_COUNT_KEY,article.getId().toString())).longValue()))
        for (Article article:articles) {
            Integer value = redisCache.getCacheMapValue(SystemConstant.REDIS_VIEW_COUNT_KEY, article.getId().toString());
            article.setViewCount(value.longValue());
        }
        List<HotArticleListVo> articleVos = BeanCopyUtil.copyBeanList(articles, HotArticleListVo.class);
        return ResponseResult.okResult(articleVos);
    }

    /**
     * 查询首页或者分类页面文章列表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getArticleList(Integer pageNum,Integer pageSize,Long categoryId) {
        //条件1 查询正式发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,SystemConstant.ARTICLE_STATUS_NORMAL);
        //有分类ID则根据分类ID查询，没有则直接查
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //条件2 置顶的文章要先是在最前面
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查
        Page<Article> page =new Page<>(pageNum,pageSize);
        List<Article> articleList = page(page, queryWrapper).getRecords();
        //把categoryName数据给封装进去
        List<Category> categoryList = categoryMapper.selectList(null);
        for (Article article: articleList) {
            //封装redis缓存的浏览量数据
            Integer viewCount = redisCache.getCacheMapValue(SystemConstant.REDIS_VIEW_COUNT_KEY, article.getId().toString());
            article.setViewCount(viewCount.longValue());
            for (Category category: categoryList) {
                if (Objects.equals(article.getCategoryId(), category.getId())){
                    article.setCategoryName(category.getName());
                }
            }
        }
        List<ArticleListVo> articleListVos = BeanCopyUtil.copyBeanList(articleList, ArticleListVo.class);
        PageResult result = new PageResult(page.getTotal(),articleListVos);
        return ResponseResult.okResult(result);
    }

    /**
     * 根据ID查询文章
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getArticleById(Long id) {
        Article article = getById(id);
        //从redis获取浏览量
        Integer viewCount = redisCache.getCacheMapValue(SystemConstant.REDIS_VIEW_COUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());
        ArticleVo articleVo = BeanCopyUtil.copyBean(article, ArticleVo.class);
        if (!Objects.isNull(articleVo.getCategoryId())){
            Category category = categoryMapper.selectById(articleVo.getCategoryId());
            articleVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(SystemConstant.REDIS_VIEW_COUNT_KEY,id.toString(),1);
        return ResponseResult.okResult();
    }
}
