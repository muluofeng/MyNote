package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author xiexingxing
 * @Created by 2018-12-07 6:56 PM.
 */
@NoRepositoryBean
public interface BaseDAO<T, ID extends Serializable> extends JpaRepository<T,ID> {
}
