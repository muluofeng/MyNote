package com.example.xing.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 被观察者
 *
 * @author xiexingxing
 * @Created by 2019-08-19 21:52.
 */
public class ObservableExtends extends Observable {
    /**
     * 添加
     *
     * @param o
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }


    /**
     * 这个方法主动触发观察者事件
     */
    public void sendMessage() {
        System.out.println("被观察者发送消息...");
        //通知 观察者
        this.setChanged();
        this.notifyObservers("我是被观察者发送的消息...");
    }
}
