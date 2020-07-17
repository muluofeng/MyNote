package com.example.xing.juc;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  线程池参数
 * @author xiexingxing
 * @Created by 2020-06-02 16:42.
 */
public class ThreadPoolExcutor {
    public static void main(String[] args) {

//        ThreadPoolExecutor(int corePoolSize,
//        int maximumPoolSize,
//        long keepAliveTime,
//        TimeUnit unit,
//        BlockingQueue<Runnable> workQueue,
//        ThreadFactory threadFactory,
//        RejectedExecutionHandler handler)
        ThreadPoolExecutor threadPoolExcutor = new ThreadPoolExecutor(
                2, //核心线程数
                5, //最大线程数
                100L, //多余线程数存活的时间
                TimeUnit.SECONDS,  // 时间单位
                new LinkedBlockingQueue<>(3), // 核心线程数满了 存放 到队列
                Executors.defaultThreadFactory(),        // 创建线程的工厂类
                new ThreadPoolExecutor.AbortPolicy());   //当任务满了的拒绝策略 5 个核心 + 3个等待的  = 8 个 ，如果超过8个 就会发生拒绝
        try {
            for (int i = 1; i <= 8; i++) {
                int finalI = i;
                Thread.sleep(100);
                threadPoolExcutor.execute(()->{
                    System.out.println(Thread.currentThread().getName() +" 开始办理业务" + finalI);

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExcutor.shutdown();
        }


    }
}