package com.example.xing.dao;

import com.example.xing.model.User;

/**
 * @author xiexingxing
 * @Created by 2018-09-06 上午12:16.
 */
public interface UserDAO extends BaseDAO<User,Integer> {
    User findByUsername(String username);
}
