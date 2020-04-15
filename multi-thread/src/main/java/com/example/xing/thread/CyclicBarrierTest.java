package com.example.xing.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-07-14 15:31.
 */
public class CyclicBarrierTest {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "--所有任务执行完了...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public static void main(String[] args) {
        MyThread a = new MyThread("A", cyclicBarrier);
        MyThread b = new MyThread("B", cyclicBarrier);
        MyThread c = new MyThread("C", cyclicBarrier);
        a.start();
        b.start();
        c.start();

    }

    static class MyThread extends Thread {
        CyclicBarrier cyclicBarrier;

        public MyThread(String name, CyclicBarrier cyclicBarrier) {
            super(name);
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "正在执行");
            try {
                sleep(3000);
                System.out.println(Thread.currentThread().getName() + "执行结束");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "--继续执行任务");
        }
    }
}
