package com.example.demo.service;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author xiexingxing
 * @Created by 2019-06-30 10:12.
 */
@Service
public class AccountServiceTestCache {

    @Autowired
    AccountDAO dao;

    @Cacheable(value = "account_test")
    public Object findAll() {
        return dao.findAll();
    }

    @Cacheable(value = "account_findById", key = "#p0")
    public Object findById(int id) {
        Optional<Account> byId = dao.findById(id);
        return byId.get();
    }

    @CachePut(value = "account_findById", key = "#p0")
    public Object updateById(int id, String name) {
        Optional<Account> byId = dao.findById(id);
        Account account = byId.get();
        account.setName(name);
        return dao.save(account);
    }

    @CacheEvict(value = "account_findById", key = "#p0")
    public void deleteById(int id) {
        dao.deleteById(id);

    }
}
