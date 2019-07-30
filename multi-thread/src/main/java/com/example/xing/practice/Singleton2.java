package com.example.xing.practice;

/**
 * @author xiexingxing
 * @Created by 2019-05-26 5:19 PM.
 */
public class Singleton2 {

    public static Singleton2 instance = null;

    private Singleton2() {

    }

    public synchronized static Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}
