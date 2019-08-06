package com.example.demo;

import com.example.xing.testImportBeanDefinitionRegistrar.annotation.MyScanner;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;


//@EnableStorage
@EntityScan({"com.example.demo.entity"})
@EnableJpaRepositories(value = "com.example.demo.dao", repositoryBaseClass = SimpleJpaRepository.class)
@EnableTransactionManagement
@EnableCaching   //开启缓存
@EnableRedisHttpSession
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class})

@MyScanner
//测试自己的注解类
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RedissonClient getRedisson() {
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer() //这是用的集群server
                .setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(config);

        try {
            System.out.println("redisson----"+redisson.getConfig().toJSON().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
        return redisson;
    }





}
