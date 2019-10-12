package com.example.demo.model;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2019-10-01 15:17.
 */
@Data
public class MybatisUser {
    private Integer id;
    private String name;
    private MybatisDept dept;
}