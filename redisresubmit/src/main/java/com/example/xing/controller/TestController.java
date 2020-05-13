package com.example.xing.controller;

import com.example.xing.annotation.Resubmit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiexingxing
 * @Created by 2020-05-13 11:17.
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @PostMapping("save")
    @Resubmit(delaySeconds = 30)
    public Object save(@RequestBody HashMap map) throws InterruptedException {
        System.out.println(map.get("xxx"));
        log.info("保存处理中...");
        Thread.sleep(60*1000);
        return  "保存成功";
    }
}