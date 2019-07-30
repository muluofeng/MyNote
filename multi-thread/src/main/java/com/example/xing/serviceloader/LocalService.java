package com.example.xing.serviceloader;

/**
 * @author xiexingxing
 * @Created by 2019-07-17 19:53.
 */
public class LocalService implements IService {
    @Override
    public String sayHello() {
        return "Hello Local!!";
    }

    @Override
    public String getScheme() {
        return "local";
    }

}
