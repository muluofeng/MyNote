package com.example.xing.config;

import com.example.xing.shiro.RedisShiroSessionDAO;
import com.example.xing.shiro.UserRealm;
import com.google.common.collect.Maps;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

/**
 * Shiro的配置文件
 */
@Configuration
public class ShiroConfig {

    @Bean("sessionManager")
    public SessionManager sessionManager(RedisShiroSessionDAO redisShiroSessionDAO, @Value("${framework.redis.open}") boolean redisOpen, @Value("${framework.shiro.redis}") boolean shiroRedis) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        // 如果开启redis缓存且framework.shiro.redis=true，则shiro session存到redis里
        if (redisOpen && shiroRedis) {
            sessionManager.setSessionDAO(redisShiroSessionDAO);
        }
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, @Value("${front.url}") String domain) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        shiroFilter.setLoginUrl("/admin/sys/login");
        shiroFilter.setUnauthorizedUrl("/login");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/statics/**", "anon");
        filterMap.put("/admin/sys/login", "anon");
        filterMap.put("/admin/sys/logout", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/h5/**", "anon");   //h5页面
        filterMap.put("/css/**", "anon");   //静态资源
        filterMap.put("/img/**", "anon");   //静态资源
        filterMap.put("/wx/**", "anon");   //微信公众号
        filterMap.put("/**", "authc");
        filterMap.put("/upload/*", "authc");    //图片资源
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        //自定义过滤, 过滤Option 请求
        Map<String, Filter> customFilter = Maps.newHashMap();

        //filter 不能使用注入的方式,会改变Filter链初始化顺序
        customFilter.put(DefaultFilter.authc.toString(), new CORSAuthenticationFilter(domain));
        shiroFilter.setFilters(customFilter);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}


