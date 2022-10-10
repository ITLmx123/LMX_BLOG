package com.limengxiang.service.impl;

import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.LoginUser;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.User;
import com.limengxiang.domain.vo.BlogLoginUserVo;
import com.limengxiang.domain.vo.UserInfoVo;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.exception.SystemException;
import com.limengxiang.service.BlogLoginService;
import com.limengxiang.utils.BeanCopyUtil;
import com.limengxiang.utils.JwtUtil;
import com.limengxiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw new SystemException(ResponseCodeEnum.LOGIN_ERROR);
        }
        //获取ID生成token
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //存入登陆用户信息到redis
        redisCache.setCacheObject(SystemConstant.REDIS_USER_KEY_HEARD +userId,loginUser);
        //设置redis有效时间
        redisCache.expire(SystemConstant.REDIS_USER_KEY_HEARD +userId,SystemConstant.REDIS_KEY_DEFAULT_EXPIRED_TIME);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogLoginUserVo blogLoginUserVo = new BlogLoginUserVo(jwt,userInfoVo);
        return ResponseResult.okResult(blogLoginUserVo);
    }

    @Override
    public ResponseResult logout() {
        //从spring全局管理获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //token解析获取登录用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取用户Id
        Long userId = loginUser.getUser().getId();
        //根据key删除redisCache
        redisCache.deleteObject(SystemConstant.REDIS_USER_KEY_HEARD+userId);
        return ResponseResult.okResult();
    }
}
