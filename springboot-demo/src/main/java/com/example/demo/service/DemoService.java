package com.example.demo.service;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiexingxing
 * @Created by 2019-06-26 17:24.
 */
@Service
public class DemoService {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDAO dao;

    @Transactional
    public void testAccount() throws Exception {
        Account account1 = new Account();
        account1.setName("test1");
        dao.save(account1);

        if (true)  {
            throw new RuntimeException("异常");

        }

        Account account = new Account();
        account.setName("test2");
        accountService.methodB(account);
    }

    // 因为accountService.addMantatory 方法事务级别为 Mantatory，所以这里外部方法调用必须有事务
    @Transactional
    public void testMantatory() throws Exception {
        Account account = new Account();
        account.setName("test2");
        accountService.addMantatory(account);
    }

    // 因为accountService.addNEVER 方法事务级别为 NEVER，所以这里外部方法调用必须 没有有事务
    @Transactional
    public void testAddNEVER() throws Exception {
        Account account = new Account();
        account.setName("test2");
        accountService.addNEVER(account);
    }



}
