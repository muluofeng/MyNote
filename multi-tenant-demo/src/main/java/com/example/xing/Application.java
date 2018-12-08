package com.example.xing;

import com.example.xing.config.MultiTenantFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EntityScan({"com.example.xing.entity"})
@EnableJpaRepositories(value = "com.example.xing.dao",repositoryBaseClass = SimpleJpaRepository.class)
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public FilterRegistrationBean registerOpenEntityManagerInViewFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(5);
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean tenanyFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        MultiTenantFilter multiTenantFilter = new MultiTenantFilter();
        registrationBean.setFilter(multiTenantFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
