package com.example.demo.dao;


import com.example.demo.entity.Good;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiexingxing
 * @Created by 2018-12-07 6:55 PM.
 */
public interface GoodDAO extends BaseDAO<Good, Integer> {

    @Query(value = " update tb_goods set num = num-1 where id=1 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateGoodsNum();

    @Query(value = " update tb_goods set num2 = num2-1 where id=1 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateGoodsNum2();
}
