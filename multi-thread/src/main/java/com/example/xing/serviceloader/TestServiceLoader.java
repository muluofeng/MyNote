package com.example.xing.serviceloader;

import java.util.ServiceLoader;

/**
 * @author xiexingxing
 * @Created by 2019-07-17 19:51.
 */
public class TestServiceLoader {
    public static void main(String[] args) {
        ServiceLoader<IService> load = ServiceLoader.load(IService.class);
        for (IService service : load) {
            System.out.println(service.getScheme() + "=" + service.sayHello());
        }
    }
}
