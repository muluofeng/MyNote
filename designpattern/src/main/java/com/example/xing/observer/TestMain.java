package com.example.xing.observer;

import java.util.Observer;

/**
 * @author xiexingxing
 * @Created by 2019-08-19 21:59.
 */
public class TestMain {
    public static void main(String[] args) {
        ObservableExtends observable = new ObservableExtends();
        Observer observer = new ObserverImp();
        Observer observer2 = new ObserverImp();
        Observer observer3 = new ObserverImp();
        observable.addObserver(observer);
        observable.addObserver(observer2);
        observable.addObserver(observer3);


        observable.sendMessage();


    }
}
