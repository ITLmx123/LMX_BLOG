package com.limengxiang.runner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.entity.Article;
import com.limengxiang.mapper.ArticleMapper;
import com.limengxiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleMapper articleMapper;


    @Scheduled(cron = "0 0/5 * * * ? ")
    public void updateViewCount(){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstant.REDIS_VIEW_COUNT_KEY);
        List<Article> viewCountList = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        for (Article article:viewCountList) {
            UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",article.getId());
            updateWrapper.set("view_count",article.getViewCount());
            articleMapper.update(null,updateWrapper);
        }
    }
}
