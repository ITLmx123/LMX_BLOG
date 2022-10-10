package com.limengxiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.limengxiang.domain.LoginUser;
import com.limengxiang.domain.entity.User;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.exception.SystemException;
import com.limengxiang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<com.limengxiang.domain.entity.User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new SystemException(ResponseCodeEnum.USERNAME_NO_EXIST);
        }
        return new LoginUser(user);
    }
}
