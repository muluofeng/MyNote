package com.example.xing.testImportBeanDefinitionRegistrar;

import com.example.xing.testImportBeanDefinitionRegistrar.annotation.HTTPRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author xiexingxing
 * @Created by 2019-07-21 17:56.
 */
public class MyRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware, BeanFactoryAware {
    ResourceLoader resourceLoader;
    BeanFactory beanFactory;
    ClassLoader classLoader;
    Environment environment;

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        registerHttpRequest(beanDefinitionRegistry);

    }

    /**
     * 注册动态bean的主要方法
     *
     * @param beanDefinitionRegistry
     */
    private void registerHttpRequest(BeanDefinitionRegistry beanDefinitionRegistry) {
//        ClassPathScanningCandidateComponentProvider classScanner = getClassScanner();
//        classScanner.setResourceLoader(this.resourceLoader);
//        //指定只关注标注了@HTTPUtil注解的接口
//        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MyScanner.class);
//        classScanner.addIncludeFilter(annotationTypeFilter);
//        //指定扫描的基础包
//        String basePack = "com.example.xing";
//        Set<BeanDefinition> beanDefinitionSet = classScanner.findCandidateComponents(basePack);
//        for (BeanDefinition beanDefinition : beanDefinitionSet) {
//            if (beanDefinition instanceof AnnotatedBeanDefinition) {
//                registerBeans(((AnnotatedBeanDefinition) beanDefinition));
//            }
//        }

        //注册一个bean
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class);
        beanDefinitionRegistry.registerBeanDefinition("userDao", beanDefinitionBuilder.getBeanDefinition());
    }

    /**
     * 创建动态代理，并动态注册到容器中
     *
     * @param annotatedBeanDefinition
     */
    private void registerBeans(AnnotatedBeanDefinition annotatedBeanDefinition) {
        String className = annotatedBeanDefinition.getBeanClassName();
        ((DefaultListableBeanFactory) this.beanFactory).registerSingleton(className, createProxy(annotatedBeanDefinition));
    }

    /**
     * 构造Class扫描器，设置了只扫描顶级接口，不扫描内部类
     *
     * @return
     */
    private ClassPathScanningCandidateComponentProvider getClassScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isInterface()) {
                    try {
                        Class<?> target = ClassUtils.forName(
                                beanDefinition.getMetadata().getClassName(),
                                classLoader);
                        return !target.isAnnotation();
                    } catch (Exception ex) {
//                        log.error("load class exception:", ex);
                    }
                }
                return false;
            }
        };
    }

    /**
     * 创建动态代理
     *
     * @param annotatedBeanDefinition
     * @return
     */
    private Object createProxy(AnnotatedBeanDefinition annotatedBeanDefinition) {
        try {
            AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
            Class<?> target = Class.forName(annotationMetadata.getClassName());
            InvocationHandler invocationHandler = createInvocationHandler();
            Object proxy = Proxy.newProxyInstance(HTTPRequest.class.getClassLoader(), new Class[]{target}, invocationHandler);
            return proxy;
        } catch (ClassNotFoundException e) {
//            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 创建InvocationHandler，将方法调用全部代理给DemoHttpHandler
     *
     * @return
     */
    private InvocationHandler createInvocationHandler() {
        return new InvocationHandler() {
            private DemoHttpHandler demoHttpHandler = new DemoHttpHandler();

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return demoHttpHandler.handle(method);
            }
        };
    }
}
