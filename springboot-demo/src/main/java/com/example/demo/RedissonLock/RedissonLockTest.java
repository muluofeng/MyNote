package com.example.demo.RedissonLock;

import com.example.demo.dao.GoodDAO;
import com.example.demo.entity.Good;
import com.example.demo.service.GoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2019-07-28 18:08.
 */
@RestController
@RequestMapping("/test")
public class RedissonLockTest {
    @Autowired
    GoodDAO goodDAO;
    @Autowired
    GoodService goodService;


    int count = 0;
    int lockcount = 0;

    @Autowired
    DistributedLocker locker;


    @RequestMapping("/reset")
    public Object reset() {
        count = 0;
        lockcount = 0;
        return null;
    }


    @RequestMapping("/updateGoodsNum1")
    public Object updateGoodsNum1() {
        goodService.reduceGoodNum();
        return null;
    }


    @RequestMapping("/updateGoodsNum2")
    public Object updateGoods() {
        goodService.reduceGoodNum2();
        return null;
    }

    @RequestMapping("/updateGoodsNum3")
    public Object updateGoods3() {
        int goodsId = 1;
        int reduceNum = 5;
        Good good = goodDAO.findById(1).get();
        synchronized (this) {
            goodService.reduceGoodNum3(goodsId, reduceNum);
        }
        return null;
    }

    @RequestMapping("/Redisson")
    public Object test() {
        increment();
        incrementLock();
        return null;
    }


    public void increment() {
        count++;
        System.out.println("count-----" + count);

    }

    public void incrementLock() {

        String key = "redisson_key";
/*
        boolean isGetLock = locker.tryLock(key, TimeUnit.SECONDS, 5L, 10L); //尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
        if (isGetLock) {

            System.out.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
            //distributedLocker.unlock(key);
            System.out.println("=============================" + Thread.currentThread().getName());
        } else {
            System.out.println("======没有获取锁======" + Thread.currentThread().getName());

        }*/

        locker.lock(key);
        lockcount++;
        locker.unlock(key);
        System.out.println("lockcount-----" + lockcount);

    }

    public void updateGoodsNum() {
        goodDAO.updateGoodsNum();
    }

    public void updateGoodsNum2() {
        String key = "redisson_key2";
        locker.lock(key);
        goodDAO.updateGoodsNum2();
        locker.unlock(key);
    }
}
