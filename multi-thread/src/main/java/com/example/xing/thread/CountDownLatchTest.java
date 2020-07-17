package com.example.xing.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-07-14 13:20.
 */
public class CountDownLatchTest {
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) {
        MyThread thread1 = new MyThread("线程1");
        MyThread thread2 = new MyThread("线程2");
        MyThread thread3 = new MyThread("线程3");
        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("等待1,2,3个子线程执行完毕...");
        try {
            countDownLatch.await();
            System.out.println("开始执行自己的任务...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//       模拟复用 ，countDownLatch 不能复用
//        MyThread thread4 = new MyThread("线程4");
//        MyThread thread5 = new MyThread("线程5");
//        MyThread thread6 = new MyThread("线程6");
//        thread4.start();
//        thread5.start();
//        thread6.start();
//
//        System.out.println("等待4,5,6个子线程执行完毕...");
//        try {
//            countDownLatch.await();
//            System.out.println("开始执行自己的任务...");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }

    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
            try {
                sleep(3000);
                System.out.println("子线程" + Thread.currentThread().getName() + "执行结束");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
