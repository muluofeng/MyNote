package com.example.xing;

import com.example.xing.dao.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiexingxing
 * @Created by 2018-12-07 9:36 PM.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping
    @ResponseBody
    public Object getTest() {
        return userDAO.findById(1);
    }
}
