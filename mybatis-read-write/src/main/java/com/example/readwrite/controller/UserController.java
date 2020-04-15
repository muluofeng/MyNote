package com.example.readwrite.controller;

import com.example.readwrite.entity.Users;
import com.example.readwrite.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2020-04-14 17:54.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/{id}")
    public Users findById(@PathVariable Long id){
       return  userService.findById(id);
    }


    @RequestMapping("/save")
    public Users save(){
        Users users = userService.save();
        return users;
    }
}