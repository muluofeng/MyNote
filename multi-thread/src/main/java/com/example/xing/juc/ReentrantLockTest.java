package com.example.xing.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiexingxing
 * @Created by 2020-05-31 14:43.
 */
public class ReentrantLockTest {

//    可重入锁又叫递归锁，指的同一个线程在外层方法获得锁时，进入内层方法会自动获取锁。
//    也就是说，线程可以进入任何一个它已经拥有锁的代码块。比如get方法里面有set方法，两个方法都有同一把锁，得到了get的锁，就自动得到了set的锁。
//    就像有了家门的锁，厕所、书房、厨房就为你敞开了一样。可重入锁可以避免死锁的问题。
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sendSMS();
        }).start();
        new Thread(()->{
            phone.sendSMS();

        }).start();

        new Thread(()->{
            phone.sendSMS();

        }).start();
        new Thread(()->{
            phone.sendSMS();

        }).start();
        new Thread(()->{
            phone.sendSMS();

        }).start();
    }

}

class Phone implements Runnable{
    //Synchronized TEST

    public  synchronized  void sendSMS(){
        System.out.println(Thread.currentThread().getId()+"\t"+"sendSMS()");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendEmail();
    }
    public synchronized  void sendEmail(){
        System.out.println(Thread.currentThread().getId()+"\t"+"sendEmail()");
    }

    //Reentrant TEST

    Lock lock=new ReentrantLock();
    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getId()+"\t"+"get()");
            set();
        }finally {
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getId()+"\t"+"set()");
        }finally {
            lock.unlock();
        }
    }
}