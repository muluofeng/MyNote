package com.example.demo.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xiexingxing
 * @Created by 2019-09-15 10:22.
 */
@Component
@Aspect
public class MyAspects {

    public MyAspects() {
    }

    @Pointcut(value = "@annotation(com.example.demo.aop.MyAnnatation)")
    public void pointcut() {
    }


    @Before(value = "pointcut()")
    public void Before() {
        System.out.println("MyAspects  Before");
    }


    @After(value = "pointcut()")
    public void After() {
        System.out.println("MyAspects  After");

    }

    @AfterReturning(value = "pointcut()")
    public void AfterReturning() {
        System.out.println("MyAspects  AfterReturning");

    }

    @AfterThrowing(value = "pointcut()")
    public void AfterThrowing() {
        System.out.println("MyAspects  AfterThrowing");

    }

}
