package com.example.xing.testImportBeanDefinitionRegistrar;

import java.lang.reflect.Method;

/**
 * @author xiexingxing
 * @Created by 2019-07-22 17:55.
 */
public class DemoHttpHandler {
    public Object handle(Method method) {
        System.out.println("处理handle");
        return new Object();
    }
}
