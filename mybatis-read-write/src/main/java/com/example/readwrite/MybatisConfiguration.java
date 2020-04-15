package com.example.readwrite;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Author: JetslyLi
 * Date: 2015/8/26
 * Description:
 */
@Configuration
@MapperScan(basePackages = "com.example.readwrite.dao")
public class MybatisConfiguration {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicRoutingDataSource dataSource, DynamicDataSourcePlugin plugin) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver
                .getResources("classpath*:/mapper/*.xml"));
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{plugin});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @ConfigurationProperties("write.jdbc")
    public DataSource writeDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("read.jdbc")
    public DataSource readDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public DynamicRoutingDataSource dataSource(DataSource writeDataSource, DataSource readDataSource) {
        return new DynamicRoutingDataSource(readDataSource, writeDataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DynamicRoutingDataSource dataSource){
        return new DynamicDataSourceTransactionManager(dataSource);
    }

    @Bean
    public DynamicDataSourcePlugin plugin(){
        DynamicDataSourcePlugin myDynamicDataSourcePlugin = new DynamicDataSourcePlugin();
        return myDynamicDataSourcePlugin;
    }
}

