package com.limengxiang.common;

public class SystemConstant {
    /**
     * 文章状态为草稿
     */
    public final static String ARTICLE_STATUS_DRAFT="1";
    /**
     * 文章状态为正常
     */
    public final static String ARTICLE_STATUS_NORMAL="0";
    /**
     * 分类状态为禁用
     */
    public final static String CATEGORY_STATUS_DISABLED="1";
    /**
     * 分类状态为正常
     */
    public final static String CATEGORY_STATUS_NORMAL="0";
    /**
     * 友链审核状态为通过
     */
    public final static String LINK_STATUS_NORMAL="0";
    /**
     * redis存入登录用户信息的key头部
     */
    public final static String REDIS_USER_KEY_HEARD="blogLogin:";
    /**
     * redis默认过期时间（默认单位秒）
     */
    public final static Long REDIS_KEY_DEFAULT_EXPIRED_TIME=12*60*60L;
    /**
     * redis文章浏览量存储key
     */
    public final static String REDIS_VIEW_COUNT_KEY="article:viewCount";
    /**
     * 文章评论默认类型
     */
    public final static String ARTICLE_COMMENT_TYPE="0";
    /**
     * 友链评论默认类型
     */
    public final static String LINK_COMMENT_TYPE="1";
}
