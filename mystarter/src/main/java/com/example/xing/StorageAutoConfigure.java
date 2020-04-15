package com.example.xing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配的类
 *
 * @author xiexingxing
 * @Created by 2019-07-17 16:23.
 * @Configuration 被该注解注释的类会提供一个或则多个@bean修饰的方法并且会被spring容器处理来生成bean definitions
 * @ConditionalOnClass 注解是条件判断的注解，表示对应的类在classpath目录下存在时，才会去解析对应的配置文件
 * @EnableConfigurationProperties 注解给出了该配置类所需要的配置信息类，也就是StorageServiceProperties类，
 * 这样spring容器才会去读取配置信息到StorageServiceProperties对象中
 * @ConditionalOnMissingBean 注解也是条件判断的注解，表示如果不存在对应的bean条件才成立，这里就表示如果已经有StorageService的bean了，
 * 那么就不再进行该bean的生成。这个注解十分重要，涉及到默认配置和用户自定义配置的原理。
 * 也就是说用户可以自定义一个StorageService的bean,这样的话，spring容器就不需要再初始化这个默认的bean了
 * @ConditionalOnProperty 注解是条件判断的注解，表示如果配置文件中的响应配置项数值为true,才会对该bean进行初始化
 */


@Configuration
@ConditionalOnClass(StorageService.class)
@EnableConfigurationProperties(StorageServiceProperties.class)
public class StorageAutoConfigure {
    @Autowired
    private StorageServiceProperties properties;

    @Bean
    @ConditionalOnMissingBean(StorageService.class)
    @ConditionalOnProperty(prefix = "storage.service", value = "enabled", havingValue = "true")
    StorageService exampleService() {
        return new StorageService(properties);
    }
}