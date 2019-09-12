package com.example.multidatasource;

import com.example.multidatasource.datasources.DataSourceNames;
import com.example.multidatasource.datasources.annotation.DataSource;
import com.example.multidatasource.entity.Signin;
import com.example.multidatasource.entity.User;
import com.example.multidatasource.service.SigninService;
import com.example.multidatasource.service.UserService;

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

    @DataSource(name = DataSourceNames.SECOND)
    public Signin findSign(Long userId) {
        return signinService.findOne(1L);
    }
}
