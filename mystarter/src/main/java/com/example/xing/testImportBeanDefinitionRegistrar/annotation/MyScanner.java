package com.example.xing.testImportBeanDefinitionRegistrar.annotation;

import com.example.xing.testImportBeanDefinitionRegistrar.MyRegistrar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiexingxing
 * @Created by 2019-07-22 17:47.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MyRegistrar.class)
@Documented
public @interface MyScanner {
}
