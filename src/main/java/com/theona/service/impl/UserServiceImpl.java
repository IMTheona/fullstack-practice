package com.theona.service.impl;

import com.theona.mapper.UserMapper;
import com.theona.pojo.User;
import com.theona.service.UserService;
import com.theona.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String Md5psw = Md5Utils.encrypt(password);
        // 添加至数据库
        userMapper.add(username,Md5psw);
    }
}
