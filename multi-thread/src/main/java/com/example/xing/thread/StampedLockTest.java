package com.example.xing.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 14:36.
 */
public class StampedLockTest {
    public static void main(String[] args) {
        testReadWriteLock();
    }

    private static void testReadWriteLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();
        Map<String, Object> map = new HashMap<>();
        Runnable task = () -> {

            long stamp = lock.writeLock();
            try {
                TimeUnit.SECONDS.sleep(2);
                map.put("a", "AAA");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockWrite(stamp);
            }
        };
        Runnable task2 = () -> {
            long stamp = lock.readLock();
            try {
                System.out.println(map.get("a"));
            } finally {
                lock.unlockRead(stamp);
            }
        };
        executorService.submit(task2);

        executorService.submit(task);
        executorService.submit(task2);
    }
}
