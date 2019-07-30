package com.example.xing.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 11:55.
 */
public class ReentrantLockTest {
    //ReentrantLock 是 互斥锁
    public ReentrantLock lock = new ReentrantLock();
    int count = 0;

    public static void main(String[] args) throws InterruptedException {
//        lockTest();
        lockTest2();
    }

    public static void lockTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ReentrantLockTest concurrencyTest = new ReentrantLockTest();

        IntStream.range(0, 100000).forEach((i) -> {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    concurrencyTest.incrementLock();
                }
            });
        });
        //停止线程，不然还有在执行的(线程池只有两个线程)，就会不准确
        stopService(executorService);
        System.out.println(concurrencyTest.count);
    }

    public static void lockTest2() {
        ReentrantLock lock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            lock.lock();
            try {
                System.out.println("Held by me: " + lock.isHeldByCurrentThread());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        executorService.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            //休眠获取锁
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        stopService(executorService);

    }


    public static void stopService(ExecutorService executorService) {
        try {
            System.out.println("attempt to shutdown executor");
            executorService.shutdown();
            //executor通过等待指定的时间让当前执行的任务终止来“温柔的”关闭executor。在等待最长5分钟的时间后，execuote最终会通过中断所有的正在执行的任务关闭。
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    public void increment() {
        //没有锁的情况下会出现问题
        count = count + 1;

    }

    public synchronized void incrementSynchronized() {
        //添加锁就就不会出现问题
        count = count + 1;

    }

    public void incrementLock() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }


}
