package com.example.demo.aop;

import org.springframework.stereotype.Component;

/**
 * @author xiexingxing
 * @Created by 2019-09-15 10:29.
 */
@Component
public class MyComponent {

    public MyComponent() {
    }

    @MyAnnatation(name = "123")
    public void test() {
        System.out.println("执行 MyComponent 方法 ");
    }
}
