package com.example.xing.testImportBeanDefinitionRegistrar;

import com.example.xing.testImportBeanDefinitionRegistrar.annotation.Select;

/**
 * @author xiexingxing
 * @Created by 2019-07-22 18:07.
 */
public interface UserDao {
    @Select(value = "select * from xxx")
    public void  getUserList();
}
