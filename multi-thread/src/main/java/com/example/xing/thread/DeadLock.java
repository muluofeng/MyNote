package com.example.xing.thread;

import java.io.File;

/**
 * @author xiexingxing
 * @Created by 2019-05-25 3:09 PM.
 */
public class DeadLock {
    public static String obj1 = "obj1";
    public static String obj2 = "obj2";

    public static void main(String[] args) {
        //死锁演示
//        Thread a = new Thread(new Lock1());
//        Thread b = new Thread(new Lock2());
//        a.start();
//        b.start();

        //打印文件夹下的目录
        printDir(new File("/Users/xing/workspaces/java/MyNote/multi-thread"), 0);
    }

    private static void printDir(File file, int level) {
        level++;
        if (file.isDirectory()) {
            for (int i = 0; i < level; i++) {
                System.out.printf("-");
            }
            System.out.println(file.getName());
            for (File f : file.listFiles()) {

                for (int i = 0; i < level; i++) {
                    System.out.printf("-");
                }
                printDir(f, level);
            }
        } else {
            System.out.println(file.getName());
        }
    }
}

class Lock1 implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Lock1 running");
            while (true) {
                synchronized (DeadLock.obj1) {
                    System.out.println("Lock1 lock obj1");
                    //获取obj1后先等一会儿，让Lock2有足够的时间锁住obj2
                    Thread.sleep(3000);
                    synchronized (DeadLock.obj2) {
                        System.out.println("Lock1 lock obj2");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Lock2 implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Lock2 running");
            while (true) {
                synchronized (DeadLock.obj2) {
                    System.out.println("Lock2 lock obj2");
                    Thread.sleep(3000);
                    synchronized (DeadLock.obj1) {
                        System.out.println("Lock2 lock obj1");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}