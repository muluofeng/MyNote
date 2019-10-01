package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * 把Spring底层一些组件注入到自定义的Bean中
 *
 * @author xiexingxing
 * @Created by 2019-09-14 19:56.
 */
@Component
public class MyAware implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    ApplicationContext applicationContext;
    /**
     * 获取当前bean注册到容器的name
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("MyAware setBeanName --" + name);
    }

    /**
     * 获取ioc容器
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String s = resolver.resolveStringValue("setEmbeddedValueResolver --${os.name}");
        System.out.println(s);
    }
}
