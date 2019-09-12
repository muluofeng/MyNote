package com.example.multidatasource.controller;

import com.example.multidatasource.service.SigninService;
import com.example.multidatasource.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2019-09-12 16:34.
 */
@RestController
@RequestMapping
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    SigninService signinService;

    @RequestMapping("/test1")
    public Object test1() {
        return userService.findOne(1L);
    }

    @RequestMapping("/test2")
    public Object test2() {
        return signinService.findOne(1L);
    }
}
