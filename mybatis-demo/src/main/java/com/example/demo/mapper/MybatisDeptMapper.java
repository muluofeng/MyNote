package com.example.demo.mapper;

import com.example.demo.model.MybatisDept;

/**
 * @author xiexingxing
 * @Created by 2019-10-01 15:20.
 */
public interface MybatisDeptMapper {

    MybatisDept findById(Integer id);


    MybatisDept findDeptCollection(Integer id);

    MybatisDept findDeptCollectionByStep(Integer id);


}