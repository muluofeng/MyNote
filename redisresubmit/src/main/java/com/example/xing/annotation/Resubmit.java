package com.example.xing.annotation;

/**
 * @author xiexingxing
 * @Created by 2020-05-13 10:59.
 */
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resubmit {

    /**
     * 延时时间 在延时多久后可以再次提交
     *
     * @return Time unit is one second
     */
    int delaySeconds() default 20;
}