package com.example.xing.factory;

/**
 * @author xiexingxing
 * @Created by 2019-08-19 22:16.
 */
public class TestMain {
    public static void main(String[] args) {

        /**
         * 1. 简单工厂的调用
         */
        StaticFactory staticFactory = new StaticFactory();
        Mouse mouse = staticFactory.createMouse(1);
        mouse.click();

    }
}
