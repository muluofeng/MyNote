package com.example.xing.jvm;


import java.util.Random;

/**
 * @author xiexingxing
 * @Created by 2019-09-01 10:11.
 */
public class Test {
    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024) + "MB");  //最大内存
        System.out.println("TOTAL_MEMORY = " + totalMemory + " （字节）" + (totalMemory / (double) 1024 / 1024) + "MB"); //总的内存

        String str = "xxx";
        while (true) {
            str += str + new Random().nextInt(2222222);
        }
    }
}
