package com.willpower.spbdm.web.controller;

import com.willpower.spbdm.web.consumer.UserConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.willpower.spbdm.model.entity.User;

import java.util.List;


/**
 * Created by Powersoft on 2019/4/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserConsumerService userService;

    @RequestMapping("")
    public String hi() {
        return "hi,sam!!!";
    }

    @RequestMapping("/all")
    public List<User> findUserAll() {
        List<User> users  = userService.findUserAll();
        return users;
    };

    @RequestMapping("/{name}")
    public User findUserByName(@PathVariable("name") String name) {
        User user = userService.findUserByName(name);
        return user;
    }
}
