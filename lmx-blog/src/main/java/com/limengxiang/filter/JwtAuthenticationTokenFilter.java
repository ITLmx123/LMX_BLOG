package com.limengxiang.filter;

import com.alibaba.fastjson.JSON;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.LoginUser;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.utils.JwtUtil;
import com.limengxiang.utils.RedisCache;
import com.limengxiang.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求中的token
        String token = request.getHeader("token");
        //如果获取不到说明该请求不带token，直接放行
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //解析token，获得userId
        Claims claims =null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时，token非法告诉前端需要重新登陆
            ResponseResult result = ResponseResult.errorResult(ResponseCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(SystemConstant.REDIS_USER_KEY_HEARD + userId);
        if (Objects.isNull(loginUser)){
            //说明登录过期  提示重新登陆失败
            ResponseResult result = ResponseResult.errorResult(ResponseCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
