package com.example.demo.initanddestory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author xiexingxing
 * @Created by 2019-09-13 11:07.
 */
@Component
public class Pc implements InitializingBean, DisposableBean {


    @Override
    public void destroy() throws Exception {
        System.out.println("pc  destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("pc  init");
    }
}
