package com.example.demo2;

import lombok.Data;

/**
 *@author xiexingxing
 *@Created by 2019-06-04 11:47.
 */

@Data
public class Users {
    private Long id;

    private String username;

    private String password;

    private Boolean userSex;

    private String nickName;
}