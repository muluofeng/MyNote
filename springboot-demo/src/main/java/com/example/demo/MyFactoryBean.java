package com.example.demo;

import com.example.demo.proxy.Black;
import com.example.demo.proxy.Color;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author xiexingxing
 * @Created by 2019-09-11 22:27.
 */
public class MyFactoryBean implements FactoryBean<Color> {
    /**
     * 返回一个color对象，这个对象会添加到容器中
     *
     * @return
     * @throws Exception
     */
    @Override
    public Color getObject() throws Exception {
        return new Black();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

}
