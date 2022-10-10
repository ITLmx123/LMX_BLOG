package com.limengxiang.service;

import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
