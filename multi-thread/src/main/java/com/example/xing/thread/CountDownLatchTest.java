package com.example.xing.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiexingxing
 * @Created by 2019-07-14 13:20.
 */
public class CountDownLatchTest {
    static  CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) {
        MyThread thread1 = new MyThread("线程1");
        MyThread thread2 = new MyThread("线程2");
        MyThread thread3 = new MyThread("线程3");
        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("等待3个子线程执行完毕...");
        try {
            countDownLatch.await();
            System.out.println("开始执行自己的任务...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread{
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
            try {
                sleep(3000);
                System.out.println("子线程"+Thread.currentThread().getName()+"执行结束");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
