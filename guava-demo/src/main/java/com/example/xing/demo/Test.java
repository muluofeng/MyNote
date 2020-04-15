package com.example.xing.demo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import lombok.SneakyThrows;

/**
 * @author xiexingxing
 * @Created by 2020-04-02 16:07.
 */
public class Test {

    @SneakyThrows({UnsupportedEncodingException.class, IOException.class})
    public String utf8ToString(byte[] bytes) {
        File file = new File("");
        file.createNewFile();
        return new String(bytes, "UTF-8");
    }
}