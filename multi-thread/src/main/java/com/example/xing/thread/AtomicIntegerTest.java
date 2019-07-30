package com.example.xing.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 18:23.
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {
        //通过使用AtomicInteger代替Integer，我们就能线程安全地并发增加数值，而不需要同步访问变量。
        // incrementAndGet()方法是原子操作，所以我们可以在多个线程中安全调用它。
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        IntStream.range(0, 100000).forEach((i) -> {
            executorService.submit(() -> {
                // +1
//                atomicInt.incrementAndGet();
                //+n
                atomicInt.updateAndGet((n) -> n + 3);
            });
        });
        ConcurrentUtils.stop(executorService);
        System.out.println(atomicInt.get());
    }
}
