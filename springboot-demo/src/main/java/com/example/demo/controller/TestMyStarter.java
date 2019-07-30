package com.example.demo.controller;

import com.example.xing.StorageService;
import com.example.xing.testImportBeanDefinitionRegistrar.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试使用自己的starter类
 *
 * @author xiexingxing
 * @Created by 2019-07-17 16:45.
 */
@RestController
@RequestMapping("/starter")
public class TestMyStarter {

    @Autowired
    StorageService storageService;


    @Autowired
    UserDao userDao;

    @RequestMapping("/test")
    public Object test() {
        return storageService;
    }

    @RequestMapping("/testuserdao")
    public void testuserdao() {
        userDao.getUserList();
    }
}

