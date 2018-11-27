package com.example.xing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author xiexingxing
 * @Created by 2018-09-06 上午12:17.
 */
@NoRepositoryBean
public interface BaseDAO<T, ID extends Serializable> extends JpaRepository<T,ID> {
}
