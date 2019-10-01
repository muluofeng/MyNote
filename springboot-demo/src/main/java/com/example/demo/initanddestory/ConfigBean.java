package com.example.demo.initanddestory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiexingxing
 * @Created by 2019-09-13 11:08.
 */
@Configuration
public class ConfigBean {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Mouse mouse() {
        return new Mouse();
    }

}
