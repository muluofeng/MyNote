package com.example.xing;

import com.example.xing.bean.TestBean;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author xiexingxing
 * @Created by 2019-07-27 16:50.
 */
public class TestXmlBenaFactory {
    public static void main(String[] args) {
        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beanfactoryTest.xml"));
        TestBean bean = (TestBean) factory.getBean("testBean");
        bean.sayHello();
    }
}
