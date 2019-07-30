package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiexingxing
 * @Created by 2019-06-30 09:50.
 */
@Service
public class TestRedis {

    @Autowired
    AccountServiceTestCache accountServiceTestCache;

    public Object findAll(){
       return  accountServiceTestCache.findAll();
    }
}
