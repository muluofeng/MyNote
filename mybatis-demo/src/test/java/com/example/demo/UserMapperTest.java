package com.example.demo;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.CustomOrder;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-06-02 10:54 PM.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() throws Exception {
        userMapper.insert(new User(1, "a1", "xxx", "", 1));
        userMapper.insert(new User(2, "a2", "xxx", "", 1));
        userMapper.insert(new User(3, "a3", "xxx", "", 1));


        Assert.assertEquals(3, userMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<User> users = userMapper.getAll();
        System.out.println(users.toString());
    }


    @Test
    public void testUpdate() throws Exception {
        User user = userMapper.getOne(5l);
        System.out.println(user.toString());
        user.setNickName("neo");
        userMapper.update(user);
        Assert.assertTrue(("neo".equals(userMapper.getOne(5l).getNickName())));
    }

    @Test
    public void testOrder() {
//        List<CustomOrder> ordersUser = userMapper.findOrdersUser();
//        List<Order> orders = userMapper.findOrdersUserResultMap();
//        List<CustomOrder> orderdetails = userMapper.findOrderdetails();
//        System.out.println(ordersUser.size());
//        System.out.println(orderdetails.size());

        List<Order> orderdetailsresultMap = userMapper.findOrderdetailsresultMap();
        System.out.println(orderdetailsresultMap.size());

    }
}
