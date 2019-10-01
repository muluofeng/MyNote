package com.example.multidatasource2;

import com.example.multidatasource2.module.signin.entity.Signin;
import com.example.multidatasource2.module.signin.service.SigninService;
import com.example.multidatasource2.module.user.entity.User;
import com.example.multidatasource2.module.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试多数据源
 */
@Service
public class DataSourceTestService {
    @Autowired
    private UserService userService;

    @Autowired
    private SigninService signinService;

    public User findUser(Long userId) {
        return userService.findOne(1L);
    }

    public Signin findSign(Long userId) {
        return signinService.findOne(1L);
    }
}
