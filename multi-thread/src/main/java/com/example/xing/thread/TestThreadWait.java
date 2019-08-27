package com.example.xing.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiexingxing
 * @Created by 2019-08-20 17:38.
 */
public class TestThreadWait {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        MyCallable myCallable1 = new MyCallable();
        MyCallable myCallable2 = new MyCallable();
//        MyCallable myCallable3 = new MyCallable();
//        Future<Integer> myCallableFuture1 = executorService.submit(myCallable1);
//        Future<Integer> myCallableFuture2 = executorService.submit(myCallable2);
//        Future<Integer> myCallableFuture3 = executorService.submit(myCallable3);
//        try {
//            Integer integer1 = myCallableFuture1.get();
//            Integer integer2 = myCallableFuture2.get();
//            Integer integer3 =  myCallableFuture3.get();
//            System.out.println(integer1+"---"+integer2+"---"+integer3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        FutureTask futureTask = new FutureTask(myCallable2);
//        Thread thread = new Thread(futureTask);
//        thread.start();
//        System.out.println("-1--");
//        futureTask.get();
//        System.out.println("--2-");


        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        myThread1.start();
        myThread2.start();
        myThread2.join();
        System.out.println("----");
        myThread1.join();
        System.out.println("-主线程-");


    }


}

