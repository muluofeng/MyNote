package com.example.xing.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  多线程 之间顺序调用  A-.B->C 启动
 *
 *  实现 A 打印5次 B 打印 10 次 C打印15次
 *
 *  继续 A 打印5次 B 打印 10 次 C打印15次
 *
 *  来10 轮
 *
 * @author xiexingxing
 * @Created by 2020-06-02 10:25.
 */
public class LockCondition {
    public static void main(String[] args) {
        Printer printer = new Printer();
            new Thread(()->{
                for (int i = 0; i < 10; i++) {
                     // 由于  print 5 第一次执行，第二次执行 print5 没达到条件就会阻塞
                    printer.print5();
                }
            },"A").start();
            new Thread(()->{
                for (int i = 0; i < 10; i++) {
                    printer.print10();
                }
                },"B").start();
            new Thread(()->{
                for (int i = 0; i < 10; i++) {
                    printer.print15();
                } },"C").start();

    }
}

class  Printer{
    public  String logo = "A";
    Lock lock = new  ReentrantLock();
    //3个条件
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            while(!logo.equals("A")){
                try {
                    System.out.println(Thread.currentThread().getName()+" 阻塞");
                    // A  阻塞
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+i);
            }
            //这里需要更改为B  ,否则即使 唤醒了，但是print10 while 条件达不到 还是会阻塞
            logo = "B";
            // 唤醒 阻塞的B
            c2.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }
    public void print10(){
        lock.lock();
        try{
            while(!logo.equals("B")){
                try {
                    System.out.println(Thread.currentThread().getName()+" 阻塞");
                    c2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+i);
            }
            logo = "C";
            // 唤醒 阻塞的C
            c3.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    public void print15(){
        lock.lock();
        try{
            while(!logo.equals("C")){
                try {
                    System.out.println(Thread.currentThread().getName()+" 阻塞");
                    c3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+i);
            }
            logo = "A";
            // 唤醒 阻塞的 A
            c1.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}