package com.example.xing.factory;

/**
 * 简单工厂，也叫静态工厂
 *
 * @author xiexingxing
 * @Created by 2019-08-19 22:10.
 */
public class StaticFactory {

    /**
     * 简单工厂  根据参数类型，产生不同的对象
     * @param type
     * @return
     */
    public Mouse createMouse(int type) {
        if (type == 1) {
            return new DellMouse();
        } else if (type == 2) {
            return new HpMouse();
        }
        return null;
    }
}
