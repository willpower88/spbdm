package com.willpower.spbdm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.willpower.spbdm.model.entity.User;
import com.willpower.spbdm.model.service.UserService;
import com.willpower.spbdm.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Powersoft on 2019/4/29.
 */
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findUserAll() {
        return userMapper.findUserAll();
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByname(name);
    }
}
