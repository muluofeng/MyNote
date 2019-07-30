package com.example.xing.serviceloader;

/**
 * @author xiexingxing
 * @Created by 2019-07-17 19:52.
 */
public class HDFSService implements IService {
    @Override
    public String sayHello() {
        return "Hello HDFS!!";
    }

    @Override
    public String getScheme() {
        return "hdfs";
    }

}
