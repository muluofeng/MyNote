package com.example.xing;

import java.io.IOException;

/**
 * @author xiexingxing
 * @Created by 2020-01-19 11:08.
 */
public class ObjectMapper {
    public static void main(String[] args) throws IOException {
        //1. 对象转成string
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        Person person = new Person();
        person.setName("xxx");
        person.setAge(1);
        String personStr = objectMapper.writeValueAsString(person);
        System.out.println(personStr); //{"name":"xxx","age":1}


        // 2. string 转成对象
        Person person2 = objectMapper.readValue(personStr, Person.class);
        System.out.println("name:" + person2.getName() + ",age" + person2.getAge()); //name:xxx,age1


    }
}