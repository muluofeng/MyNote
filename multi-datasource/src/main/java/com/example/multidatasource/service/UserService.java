package com.example.multidatasource.service;

import com.example.multidatasource.dao.UserDao;
import com.example.multidatasource.entity.User;

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
