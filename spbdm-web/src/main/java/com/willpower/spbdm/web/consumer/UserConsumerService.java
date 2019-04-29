package com.willpower.spbdm.web.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.willpower.spbdm.model.entity.User;
import com.willpower.spbdm.model.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Powersoft on 2019/4/29.
 */
@Component
public class UserConsumerService {

    @Reference(version = "1.0.0")
    UserService userService;

    public List<User> findUserAll() {
        return userService.findUserAll();
    }

    public User findUserByName(String name) {
        return userService.findUserByName(name);
    }
}
