package com.example.xing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author xiexingxing
 * @Created by 2019-07-31 17:36.
 */
@SpringBootApplication
@EntityScan({"com.example.xing.entity"})
@EnableJpaRepositories(value = {"com.example.xing.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
