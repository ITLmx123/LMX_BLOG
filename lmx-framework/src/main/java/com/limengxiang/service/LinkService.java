package com.limengxiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author LMX
 * @since 2022-09-07 10:26:10
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

