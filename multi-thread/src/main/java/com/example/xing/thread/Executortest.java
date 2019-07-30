package com.example.xing.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-06-09 11:04.
 */
public class Executortest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () ->{
                    TimeUnit.SECONDS.sleep(2);
                   return "task1";
                } ,
                () -> {
                    TimeUnit.SECONDS.sleep(1);
                    return "task2";
                },
                () ->{
//                    TimeUnit.SECONDS.sleep(1);
                    return "task3";
                });

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);



    }
}
