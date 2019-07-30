package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-06-03 3:26 PM.
 */
@Getter
@Setter
public class Order {
    private Integer id;
    private Integer userId;
    private Integer money;
    private User user;
    List<Orderdetail> orderdetails;
}
