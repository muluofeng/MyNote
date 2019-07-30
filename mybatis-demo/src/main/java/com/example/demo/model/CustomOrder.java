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
public class CustomOrder extends Order {
    private String userName;
    List<Orderdetail> orderdetails;

}
