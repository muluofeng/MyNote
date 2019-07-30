package com.example.demo.RedissonLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-07-28 17:12.
 */
@RestController
@RequestMapping("/redisson")
public class AdvMaterialController {
    @Autowired
    private DistributedLocker distributedLocker;

    @RequestMapping("/test")
    public void redissonTest() {
        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.err.println("=============线程开启============" + Thread.currentThread().getName());
					/*distributedLocker.lock(key,10L); //直接加锁，获取不到锁则一直等待获取锁
					 Thread.sleep(100); //获得锁之后可以进行相应的处理
					 System.err.println("======获得锁后进行相应的操作======"+Thread.currentThread().getName());
					 distributedLocker.unlock(key);  //解锁
					 System.err.println("============================="+Thread.currentThread().getName());*/
                        boolean isGetLock = distributedLocker.tryLock(key, TimeUnit.SECONDS, 5L, 10L); //尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        if (isGetLock) {
                            Thread.sleep(100); //获得锁之后可以进行相应的处理
                            System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
                            //distributedLocker.unlock(key);
                            System.err.println("=============================" + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }
}