package com.example.multidatasource2.module.user.service;

import com.example.multidatasource2.module.user.dao.UserDao;
import com.example.multidatasource2.module.user.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiexingxing
 * @Created by 2019-09-12 16:26.
 */
@Service
public class UserService {

    @Autowired
    UserDao dao;

    public User findOne(Long id) {
        return dao.findById(id).get();
    }
}
