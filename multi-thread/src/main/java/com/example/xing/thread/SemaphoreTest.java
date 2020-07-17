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

//        下面对上面说的三个辅助类进行一个总结：
//
//        CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：
//        CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；
//        而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；
//        另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。
//        Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限

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
