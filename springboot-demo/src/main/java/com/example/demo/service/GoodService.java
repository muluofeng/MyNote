package com.example.demo.service;

import com.example.demo.RedissonLock.DistributedLocker;
import com.example.demo.dao.GoodDAO;
import com.example.demo.entity.Good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiexingxing
 * @Created by 2019-06-17 12:08.
 */
@Service
public class GoodService {

    public ReentrantLock locke;
    @Autowired
    GoodDAO goodDAO;
    @Autowired
    DistributedLocker locker;

    public GoodService() {
        locke = new ReentrantLock();
    }

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

    @Transactional(rollbackFor = Exception.class)
    public void reduceGoodNum3(int id, int reduceNum) {
        Good good = goodDAO.findById(id).get();

        int num = good.getNum2();
        System.out.println("当前库存为：---)" + num);

        if (num - reduceNum < 0) {
            throw new RuntimeException(" 库存不够了");
        }

        if (num > 0) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            goodDAO.updateGoodsNum2();
            int currentNum = good.getNum2() - reduceNum;

            good.setNum2(currentNum);
            goodDAO.saveAndFlush(good);
            System.out.println("更新库存");
        } else {
            System.out.println("num 已经小于0了");
        }

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
