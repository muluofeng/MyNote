package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-06-02 9:07 PM.
 */
public interface UserService {

    List<User> getAll();

    User getUser(int id);

    int deleteUser(int id);

    int addUser(User u);

    int editUser(User u);
}