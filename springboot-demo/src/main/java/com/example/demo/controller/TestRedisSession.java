package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiexingxing
 * @Created by 2019-06-30 15:13.
 */
@RestController
@RequestMapping("/redissession")
public class TestRedisSession {

    @RequestMapping("/test")
    @ResponseBody
    public Object testSession(HttpServletRequest request) {
        String username = request.getParameter("username");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("username", username);
        map.put("sex", 1);
        request.getSession().setAttribute("username", map);
        return request.getSession();
    }


    @RequestMapping("/gettest")
    @ResponseBody
    public Object gettest(HttpServletRequest request) {
        return request.getSession().getAttribute("username");
    }

}
