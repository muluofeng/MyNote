package com.example.demo.proxy;

/**
 * @author xiexingxing
 * @Created by 2019-07-30 18:24.
 */
public class Person implements Animal {
    @Override
    public void sayName() {
        System.out.println("I am person");
    }
}
