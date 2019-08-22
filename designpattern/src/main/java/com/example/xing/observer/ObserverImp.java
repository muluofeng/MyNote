package com.example.xing.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 *
 * @author xiexingxing
 * @Created by 2019-08-19 21:49.
 */


public class ObserverImp implements Observer {
    public void update(Observable o, Object arg) {
        // 接收到更新
        System.out.println("接收到更新," + arg.toString());
    }
}
