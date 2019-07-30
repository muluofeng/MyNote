package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xiexingxing
 * @Created by 2019-06-02 9:06 PM.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String userName;
    private String passWord;
    private String nickName;
    private Integer userSex;


}