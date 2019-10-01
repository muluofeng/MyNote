package com.example.multidatasource2;

import com.example.multidatasource2.module.signin.entity.Signin;
import com.example.multidatasource2.module.user.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiexingxing
 * @Created by 2019-09-12 17:19.
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {
    @Autowired
    private DataSourceTestService dataSourceTestService;

    @Test
    public void test() {
        //数据源1
        User user = dataSourceTestService.findUser(1L);
        System.out.println(user.getUsername());

        //数据源2
        Signin signin = dataSourceTestService.findSign(1L);
        System.out.println(signin.getDate());

    }

}
