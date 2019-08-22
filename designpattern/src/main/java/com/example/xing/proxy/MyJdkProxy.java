package com.example.xing.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xiexingxing
 * @Created by 2019-07-30 19:21.
 */
public class MyJdkProxy implements InvocationHandler {

    Object obj;

    public MyJdkProxy(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(obj, args);
        return null;
    }
}