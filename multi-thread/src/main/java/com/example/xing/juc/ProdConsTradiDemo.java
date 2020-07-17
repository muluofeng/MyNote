package com.example.xing.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者 模式
 *  初始值为0的变量，两个线程交替操作，一个+1，一个-1，执行五轮
 * @author xiexingxing
 * @Created by 2020-06-01 17:57.
 */
public class ProdConsTradiDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        BasicShareData basicShareData = new BasicShareData();
//        lock版本测试
//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                shareData.product();
//            }).start();
//        }
//
//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                shareData.consume();
//            }).start();
//        }


        // 传统版本测试
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                basicShareData.product();
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                basicShareData.consume();
            }).start();
        }

    }
}

/**
 * 基础版本
 */
class BasicShareData{
    public  int num = 0;

    public synchronized  void product() {
        while(num!=0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num++;
        System.out.println(Thread.currentThread().getName()+"生产+1----"+num);
        this.notifyAll();
    }

    public synchronized void consume() {
        while(num==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num--;
        System.out.println(Thread.currentThread().getName()+"消费+1----"+num);
        this.notifyAll();
    }
}


/**
 *  lock 版本
 */
class ShareData {
    public  int num = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void product() {
        lock.lock();
        try {
            //如果换成if  就会出现虚假唤醒，也就是
            //  当condition.signalAll() 使得 condition.await()  不阻塞 ，导致所有的其他线程都有可能 生产+1
            //  使用while 虽然 condition.await()  不阻塞 ，但是 当有一个线程生产+1
            //  由于其他线程需要 在进行while 循环判断，已经不满足条件了（当有一个线程生产+1 ），就会进行阻塞
            while (num != 0) {
                System.out.println(Thread.currentThread().getName()+"wait 阻塞-- ");
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"生产+1----"+num);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void consume() {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            num--;
            System.out.println("消费+1----"+num);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}