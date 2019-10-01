package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author xiexingxing
 * @Created by 2019-09-14 19:42.
 */
@Component
public class ProjectTwo {

    private Person person;
    private Dog dog;

    //注入多个属性，如果只有一个构造方法 @Autowired可以省略
//    @Autowired
    public ProjectTwo(Person person, Dog dog) {
        this.person = person;
        this.dog = dog;
    }

    public void sayName() {
        person.sayName();
        System.out.println(dog.getName());
    }

}
