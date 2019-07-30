package com.example.xing.thread;

import java.util.concurrent.*;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 11:17.
 */
public class ScheduledExecutorTest {
    public static void main(String[] args) throws InterruptedException {
//        scheduleWithDelay();
//        scheduleAtFixedRate();
        scheduleWithFixedDelay();
    }

    private static void scheduleWithFixedDelay() {
        ScheduledExecutorService executor =         Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            }
            catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }

    public static void scheduleAtFixedRate() {

        /**
         * 固定频率执行任务
         */
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        Runnable task = () -> {
            try {
                System.out.println("Scheduling: " + System.nanoTime());

                TimeUnit.SECONDS.sleep(12);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        int initialDelay = 10;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    public static void scheduleWithDelay() throws InterruptedException {
        /**
         * 延迟执行任务
         */
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        //调度一个任务在延迟3分钟后执行
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        // 阻塞当前线程1337毫秒
        TimeUnit.MILLISECONDS.sleep(1337);

        //提供了getDelay()方法来获得剩余的延迟
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms", remainingDelay);
    }
}
