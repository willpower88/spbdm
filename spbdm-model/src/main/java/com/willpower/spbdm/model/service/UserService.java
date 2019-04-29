package com.willpower.spbdm.model.service;

import com.willpower.spbdm.model.entity.User;

import java.util.List;

/**
 * Created by Powersoft on 2019/4/29.
 */
public interface UserService {

    /**
     * 获取所有用户
     * @return
     */
    List<User> findUserAll();

    /**
     * 根据用户名获取用户
     * @param name
     * @return
     */
    User findUserByName(String name);
}
