package com.example.xing.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 14:22.
 */
public class ReadWriteLockTets {
    public static void main(String[] args) {
        testReadWriteLock();
    }

    private static void testReadWriteLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Map<String, Object>  map = new HashMap<>();


        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.writeLock().lock();
            try {
                System.out.println("writeLock ...");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("begin put data ...");
                map.put("a", "AAA");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        };


        Runnable task2 = () -> {

            lock.readLock().lock();
            System.out.println("readLock...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(map.get("a"));

            }  finally {
                lock.readLock().unlock();
            }
        };
        executorService.submit(task2);

        executorService.submit(task);
//        executorService.submit(task2);
    }
}
