package com.example.readwrite.dao;

import com.example.readwrite.entity.Users;

/**
 *@author xiexingxing
 *@Created by 2020-04-14 17:52.
 */

public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}