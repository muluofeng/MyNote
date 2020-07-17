package com.example.xing.oom;

import java.util.ArrayList;

/**
 *  内存泄漏测试
 * @author xiexingxing
 * @Created by 2020-06-01 10:42.
 */
public class OOM {
    public static final int K =1024;
    public static void main(String[] args) {
//        javaHeapSpace();
//        stackOverFolowError();
    }

    public static void  stackOverFolowError(){
        stackOverFolowError();
    }

    /**
     * ce是
     */
    private static void javaHeapSpace() {
        int size = K*K*8;
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < K ; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("写入" +(i+1)+"M数据");
            objects.add(new Byte[size]);
        }
    }

}