package com.example.demo;

import com.example.demo.proxy.Blue;
import com.example.demo.proxy.Cat;
import com.example.demo.proxy.Yellow;
import com.example.xing.testImportBeanDefinitionRegistrar.annotation.MyScanner;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
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

@ComponentScan(includeFilters ={
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyFilterType.class}) //自定义filter

})
//测试自己的注解类

//使用@Import 导入类
@Import({Cat.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//        Cat cat = (Cat) context.getBean("conditionBean");
//        System.out.println(cat.getName());

        Cat cat = (Cat) context.getBean(Cat.class);
        System.out.println(cat.getName());

        Blue blue = (Blue) context.getBean(Blue.class);
        System.out.println(blue.name);


        Yellow yellow = (Yellow) context.getBean(Yellow.class);
        System.out.println(yellow.name);


        Object b1 = context.getBean("myFactoryBean");  //获取factorybean 实际注册的bean
        Object b2 = context.getBean("&myFactoryBean"); //获取factorybean 本身
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


    @Bean("conditionBean")
    @Conditional(MyCondition.class)
    public Cat cat(){
       return new Cat("xx",11);
    }



    @Bean
    public MyFactoryBean myFactoryBean(){
       return  new MyFactoryBean();
    }


}
