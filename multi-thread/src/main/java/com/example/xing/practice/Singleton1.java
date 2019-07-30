package com.example.xing.practice;

/**
 * @author xiexingxing
 * @Created by 2019-05-26 5:19 PM.
 */
public class Singleton1 {

    public static Singleton1 instance = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance(){
        return instance;
    }
}
