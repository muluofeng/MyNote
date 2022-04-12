package com.example.xing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiexingxing
 * @date 2022/4/12 9:33 下午
 */

public class ThreadLocalTest {
    public  static  ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyThread("测试"+i));
        }
    }

    public static class MyThread implements Runnable {
        private String name;

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("name:"+name);
            threadLocal.remove();
            if(threadLocal.get()!=null){
                System.out.println("threadLocal.get"+threadLocal.get());
            }
            threadLocal.set(name+"i");
        }
    }
}
