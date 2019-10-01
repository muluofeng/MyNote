package com.example.demo.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiexingxing
 * @Created by 2019-09-14 19:33.
 */
@Component
public class Project {

    //    @Qualifier(value = "chinaPerson")
    @Autowired
    Person person;


    @Resource
    Person JapanPerson;

    public void sayName() {
        person.sayName();
    }

    public void sayName2() {
        JapanPerson.sayName();
    }
}
