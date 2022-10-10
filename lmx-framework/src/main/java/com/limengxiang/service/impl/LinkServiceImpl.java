package com.limengxiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Link;
import com.limengxiang.domain.vo.LinkListVo;
import com.limengxiang.mapper.LinkMapper;
import com.limengxiang.service.LinkService;
import com.limengxiang.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author LMX
 * @since 2022-09-07 10:26:11
 */
@Service("")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询状态为0的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstant.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        List<LinkListVo> linkListVos = BeanCopyUtil.copyBeanList(links, LinkListVo.class);
        return ResponseResult.okResult(linkListVos);
    }
}

