package com.example.xing.demo;

/**
 * @author xiexingxing
 * @Created by 2019-06-05 15:05.
 */

public class APIResponse<T> {
    private String message;
    private T data;
    private int code;
    public APIResponse success(){
        this.code = 200;
        return  this;
    }
}
