package com.example.xing.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @description: 多数据源配置
 **/
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({JpaProperties.class})
public class DataSourceConfig {


    /**
     * enterprise 企业平台数据源
     *
     * @return
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.enterprise")
    public DataSource enterpriseDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * enterprise 企业平台jdbc模板
     *
     * @param dataSource
     * @return
     */
    @Primary
    @Bean("enterpriseJdbcTemplate")
    public JdbcTemplate enterpriseJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
