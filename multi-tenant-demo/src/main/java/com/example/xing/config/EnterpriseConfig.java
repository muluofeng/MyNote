package com.example.xing.config;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: gcwe
 * @description: 企业数据源配置
 * @author: chenxing
 * @create: 2018-11-19 17:36
 **/

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({JpaProperties.class})
@EnableJpaRepositories(
        entityManagerFactoryRef = "enterpriseEntityManagerFactory")
public class EnterpriseConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Resource(name = "enterpriseDataSource")
    private DataSource enterpriseDataSource;


    @Bean(name = "enterpriseEntityManagerFactory")
    public EntityManager entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
                                                                           CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {
        Map<String, Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        // No dataSource is set to resulting entityManagerFactoryBean
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setPackagesToScan(new String[]{"com.example.xing.entity"});
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setDataSource(enterpriseDataSource);
        result.setJpaPropertyMap(hibernateProps);

        return result;
    }

    @Bean(name = "transactionManager")
    PlatformTransactionManager enterpriseTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager manager = new JpaTransactionManager(entityManagerFactory);
        return manager;
    }
}
