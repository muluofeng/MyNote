package com.example.demo.model;

import java.util.List;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2019-10-01 15:18.
 */
@Data
public class MybatisDept {
    /**
     * 用户列表
     */
    List<MybatisUser> user;
    private Integer id;
    private String deptname;

}