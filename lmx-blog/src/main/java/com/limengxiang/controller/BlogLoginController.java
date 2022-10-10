package com.limengxiang.controller;

import com.limengxiang.annotation.SystemLog;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.User;
import com.limengxiang.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;
    @SystemLog(businessName = "登录请求")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return blogLoginService.login(user);
    }
    @SystemLog(businessName = "退出请求")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}