package com.example.xing.testImportBeanDefinitionRegistrar;

import com.example.xing.testImportBeanDefinitionRegistrar.annotation.Select;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiexingxing
 * @Created by 2019-07-22 18:02.
 */
public class MyFactoryBean implements FactoryBean {
    public Object getObject() throws Exception {
        //用户生成代理类
        UserDao userDao = (UserDao) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{UserDao.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("调用之前...");
                Select annotation = AnnotationUtils.findAnnotation(method, Select.class);
                if (annotation == null) {
                    return new Object();
                }
                System.out.println("获取注解上的值" + annotation.value().toString());
                System.out.println("调用之后...");
                return new Object();
            }
        });
        return userDao;
    }

    public Class<?> getObjectType() {
        return UserDao.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
