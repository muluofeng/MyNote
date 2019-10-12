package com.example.demo.mapper;

import com.example.demo.model.CustomOrder;
import com.example.demo.model.Order;
import com.example.demo.model.User;

import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-06-02 9:02 PM.
 */
public interface UserMapper {


    List<CustomOrder> findOrderdetails();

    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);

    List<CustomOrder> findOrdersUser();

    List<Order> findOrdersUserResultMap();


    List<Order> findOrderdetailsresultMap();

//    List<Order> test();



}
