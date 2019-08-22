package com.example.xing.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试权限
 *
 * @author xiexingxing
 * @Created by 2019-08-07 17:38.
 */
@RestController
@RequestMapping("/test")
public class TestOperationController {
    @RequestMapping("/test")
    @RequiresPermissions("business:sysmenu:list")
    public Object test() {
        return "test1";
    }

    @RequestMapping("/test2")
    @RequiresPermissions("business:sysmenu:list2")
    public Object test2() {
        return "test2";
    }
}
