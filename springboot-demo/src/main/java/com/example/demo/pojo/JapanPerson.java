package com.example.demo.pojo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiexingxing
 * @Created by 2019-09-14 19:32.
 */
@Getter
@Setter
@Component
@Primary
public class JapanPerson implements Person {
    private String desc = "I'm JapanPerson";

    @Override
    public void sayName() {
        System.out.println("I'm JapanPerson");
    }
}
