package com.example.xing.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2018-11-28 12:46 AM.
 */
@RestController
public class TestController {

    @ResponseBody
    @RequestMapping("/needAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin(){
        return "need admin role";
    }

    @ResponseBody
    @RequestMapping("/needUser")
    @PreAuthorize("hasRole('USER')")
    public String testUser(){
        return "need user role";
    }
}
