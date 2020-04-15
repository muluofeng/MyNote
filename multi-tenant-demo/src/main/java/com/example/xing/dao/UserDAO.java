package com.example.xing.dao;

import com.example.xing.entity.User;

/**
 * @author xiexingxing
 * @Created by 2018-12-07 6:55 PM.
 */
public interface UserDAO extends BaseDAO<User, Integer> {
    User findByUsername(String username);
}
