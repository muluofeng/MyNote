package com.example.xing.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xiexingxing
 * @Created by 2019-07-30 19:34.
 */
public class CGLibProxy implements MethodInterceptor {
    // CGLib需要代理的目标对象
    private Object targetObject;

    public Object bind(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        // 设置父类--可以是类或者接口
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        // 返回代理对象
        return proxyObj;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object obj = method.invoke(targetObject, objects);
        return obj;
    }
}
