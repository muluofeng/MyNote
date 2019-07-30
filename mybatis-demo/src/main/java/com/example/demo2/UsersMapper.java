package com.example.demo2;

import com.example.demo2.Users;

/**
 *@author xiexingxing
 *@Created by 2019-06-04 11:47.
 */

public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}