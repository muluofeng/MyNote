package com.example.readwrite.service;

import com.example.readwrite.dao.UsersMapper;
import com.example.readwrite.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiexingxing
 * @Created by 2020-04-14 17:53.
 */
@Service
public class UserService
{
    @Autowired
    UsersMapper usersMapper;

    public Users findById(Long id){
        return usersMapper.selectByPrimaryKey(id);
    }

//    @Transactional
    public Users save() {
        Users users = findById(6L);
        users.setUsername("a5-写数据源");
        usersMapper.updateByPrimaryKey(users);
        return users;
    }
}