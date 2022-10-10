package com.limengxiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
