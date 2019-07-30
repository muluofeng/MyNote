package com.example.demo.service;

import com.example.demo.RedissonLock.DistributedLocker;
import com.example.demo.dao.GoodDAO;
import com.example.demo.entity.Good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-06-17 12:08.
 */
@Service
public class GoodService {

    @Autowired
    GoodDAO goodDAO;

    @Autowired
    DistributedLocker locker;


    public void reduceGoodNum2() {
        String key = "redisson_key2";
        locker.lock(key);
        int id = 1;
        Good good = goodDAO.getOne(id);
        int num = good.getNum2();
        if (num > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goodDAO.updateGoodsNum2();
            System.out.println("更新库存");
        } else {
            System.out.println("num 已经小于0了");
        }
        locker.unlock(key);
    }

    public void reduceGoodNum() {
        int id = 1;
        Good good = goodDAO.getOne(id);
        int num = good.getNum();
        if (num > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goodDAO.updateGoodsNum();
            System.out.println("更新库存");
        } else {
            System.out.println("num 已经小于0了");
        }

    }



}
