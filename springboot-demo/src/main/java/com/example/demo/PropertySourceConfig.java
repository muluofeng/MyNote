package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 使用PropertySource  导入配置文件的配置,一般需要配合 @Configuration 使用
 * @author xiexingxing
 * @Created by 2019-09-14 18:04.
 */
@Configuration
@PropertySource(value = {"classpath:/test.properties"})
public class PropertySourceConfig {
}
