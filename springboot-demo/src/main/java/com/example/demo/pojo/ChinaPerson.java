package com.example.demo.pojo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiexingxing
 * @Created by 2019-09-14 19:30.
 */
@Getter
@Setter
@Component
public class ChinaPerson implements Person {
    private String desc = "I'm chinese";

    @Override
    public void sayName() {
        System.out.println("I'm chinese");
    }
}
