package com.example.xing.proxy;


import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * @author xiexingxing
 * @Created by 2019-07-30 18:20.
 */
public class ProxyMain {
    public static void main(String[] args) {

//         System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");


        //JDK原生动态代理是Java原生支持的，不需要任何外部依赖，但是它只能基于接口进行代理；
        // CGLIB通过继承的方式进行代理，无论目标对象有没有实现接口都可以代理，但是无法处理final的情况

        //1. 使用jdk 动态代理
        Animal person = (Animal) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(),
                new Class[]{Animal.class}, new MyJdkProxy(new Person())
        );
        person.sayName();


        //输入person1的动态代理类
        Person person1 = new Person();
        generateClassFile(person1.getClass(), "PersonProxy");




        //2. 使用 cglib动态代理,不能代理final类型,如果person为final，就不能代理了
        CGLibProxy cgLibProxy = new CGLibProxy();
        Animal bind = (Animal) cgLibProxy.bind(new Person());
        bind.sayName();
    }


    public static void generateClassFile(Class clazz, String proxyName) {
        // 根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = clazz.getResource(".").getPath();
        System.out.println(paths);
        FileOutputStream out = null;
        try {
            //保留到硬盘中
            out = new FileOutputStream(paths + proxyName + ".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
