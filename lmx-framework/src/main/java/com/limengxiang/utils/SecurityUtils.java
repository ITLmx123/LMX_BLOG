package com.limengxiang.utils;

import com.limengxiang.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Lmx
 */
public class SecurityUtils
{

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        if (getAuthentication().getPrincipal()instanceof String){
            return null;
        }
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
