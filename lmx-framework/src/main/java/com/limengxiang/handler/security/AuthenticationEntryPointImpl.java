package com.limengxiang.handler.security;

import com.alibaba.fastjson.JSON;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败处理类
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){
        authException.printStackTrace();
        ResponseResult result = null;
        if (authException instanceof BadCredentialsException){
            result = ResponseResult.errorResult(ResponseCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if (authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(ResponseCodeEnum.NEED_LOGIN.getCode(),authException.getMessage());
        }else {
            result = ResponseResult.errorResult(ResponseCodeEnum.SYSTEM_ERROR.getCode(),"认证失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
