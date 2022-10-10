package com.limengxiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Article;
import com.limengxiang.domain.entity.Category;
import com.limengxiang.domain.vo.CategoryVo;
import com.limengxiang.mapper.ArticleMapper;
import com.limengxiang.mapper.CategoryMapper;
import com.limengxiang.service.CategoryService;
import com.limengxiang.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(LmxCategory)表服务实现类
 *
 * @author makejava
 * @since 2022-09-06 15:31:58
 */
@Service("")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public ResponseResult getCategoryList() {
        //条件1 只展示发布有正式文章的分类
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        Set<Long> categoryIds = articles.stream().map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //条件2 必须是正常状态的分类(0)
        List<Category> categories = listByIds(categoryIds);
        List<Category> categoryList = categories.stream()
                .filter(category -> SystemConstant.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        List<CategoryVo> result = BeanCopyUtil.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(result);
    }
}

