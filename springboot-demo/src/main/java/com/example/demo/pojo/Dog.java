package com.example.demo.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2019-09-14 18:06.
 */
@Data
@Component
public class Dog {
    @Value("${person.nickname}")
    private String name;
}
