package com.example.demo.initanddestory;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xiexingxing
 * @Created by 2019-09-13 11:11.
 */
@Component
public class Keyboard {

    @PostConstruct
    public void init() {
        System.out.println(" Keyboard init ");
    }

    @PreDestroy
    public void destroy() {
        System.out.println(" Keyboard destroy ");

    }
}
