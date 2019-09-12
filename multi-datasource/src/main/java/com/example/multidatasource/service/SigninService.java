package com.example.multidatasource.service;

import com.example.multidatasource.dao.SigninDao;
import com.example.multidatasource.entity.Signin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiexingxing
 * @Created by 2019-09-12 16:26.
 */
@Service
public class SigninService {
    @Autowired
    SigninDao dao;

    public Signin findOne(Long id) {
        return dao.findById(id).get();
    }

}
