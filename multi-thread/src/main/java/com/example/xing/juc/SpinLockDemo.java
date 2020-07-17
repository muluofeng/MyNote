package com.example.xing.juc;

import java.util.concurrent.atomic.AtomicReference;

/**
 *  实现自旋锁
 * @author xiexingxing
 * @Created by 2020-06-01 09:25.
 */
public class SpinLockDemo {

    public  AtomicReference<Thread> atomicReference =new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        for (int i = 0; i < 5 ; i++) {
            new Thread(()->{
                spinLockDemo.lock();

                System.out.println(Thread.currentThread().getName()+"  begin ");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName()+"  end ");

                spinLockDemo.unlock();
            }).start();
        }
    }



    public void lock(){
        //如果为 null ,表示没有上锁，就把当前Thread 放进去，否则一直循环
        do{

        }while (!atomicReference.compareAndSet(null,Thread.currentThread()));
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
    }
}