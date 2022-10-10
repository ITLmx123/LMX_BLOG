package com.limengxiang.controller;

import com.limengxiang.annotation.SystemLog;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.User;
import com.limengxiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @SystemLog(businessName = "获取用户信息")
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @SystemLog(businessName = "修改用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @SystemLog(businessName = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@Validated @RequestBody User user){
        return userService.register(user);
    }
}
