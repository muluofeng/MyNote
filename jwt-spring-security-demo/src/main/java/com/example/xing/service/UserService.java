package com.example.xing.service;

import com.example.xing.dao.UserDAO;
import com.example.xing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiexingxing
 * @Created by 2018-09-06 上午12:16.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
     public User findByName(String name){
         return userDAO.findByUsername(name);
     }


//    public static void main(String[] args) {
//         String data="1.088810000000234088810000000234012906877230@qq.com2311";
//         String security="27527712b072e72c35248f1c105b5534";
//        System.out.println( DigestUtils.md5Hex(data+security));
//    }
}
