package com.limengxiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.User;
import com.limengxiang.domain.vo.UserInfoVo;
import com.limengxiang.domain.vo.UserVo;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.exception.SystemException;
import com.limengxiang.mapper.UserMapper;
import com.limengxiang.service.UserService;
import com.limengxiang.utils.BeanCopyUtil;
import com.limengxiang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult userInfo() {
        User user = getById(SecurityUtils.getUserId());
        UserVo result = BeanCopyUtil.copyBean(user, UserVo.class);
        return ResponseResult.okResult(result);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        UserInfoVo userInfo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfo);
    }

    @Override
    public ResponseResult register(User user) {
        if (userNameExit(user.getUserName())){
            throw new SystemException(ResponseCodeEnum.USERNAME_EXIST);
        }if (emailExit(user.getEmail())){
            throw new SystemException(ResponseCodeEnum.EMAIL_EXIST);
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.okResult();
    }

    private Boolean userNameExit(String userName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        int count = count(queryWrapper);
        return count!=0;
    }

    private Boolean emailExit(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        int count = count(queryWrapper);
        return count!=0;
    }
}
