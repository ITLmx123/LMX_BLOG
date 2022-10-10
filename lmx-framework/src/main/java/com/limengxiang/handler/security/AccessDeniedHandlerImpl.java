package com.limengxiang.handler.security;

import com.alibaba.fastjson.JSON;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败处理类
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(ResponseCodeEnum.NO_OPERATOR_AUTH);
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
