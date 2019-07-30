package com.example.xing.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 18:07.
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        testSemaphore();
    }

    private static void testSemaphore() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        Runnable task = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(2, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    TimeUnit.SECONDS.sleep(4);
                } else {
                    System.out.println("Semaphore not acquired");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 20).forEach((i) -> {
            executorService.submit(task);
        });
    }
}
