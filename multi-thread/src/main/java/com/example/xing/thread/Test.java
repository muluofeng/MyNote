package com.example.xing.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author xiexingxing
 * @Created by 2019-05-25 9:50 AM.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        Thread thread3 = new Thread(new MyRunable());

        MyCallable myCallable = new MyCallable();
        FutureTask futureTask = new FutureTask(myCallable);
        Thread thread4 = new Thread(futureTask, "myCallable");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("futureTask.get--"+futureTask.get());


        System.out.println("--------测试join");

        for(int i=0;i<10;i++){
            System.out.println("主线程"+i);
            if(i>10){
//                thread4.join();
            }
        }
    }


}

/**
 * 继承Thread
 */
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println(this.getName() + "MyThread run ");
    }
}

/**
 *  implements Runnable
 */
class MyRunable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "MyThread run ");

    }
}

/**
 * Callable
 */
class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "--" + i);
        }
        return i;
    }
}