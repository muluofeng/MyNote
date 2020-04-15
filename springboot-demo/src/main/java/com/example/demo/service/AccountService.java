package com.example.demo.service;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-06-17 12:08.
 */
@Service
public class AccountService {

    @Autowired
    AccountDAO dao;

    /**
     * 指定 回滚的异常，如果抛出指定的异常，就会回滚
     *
     * @param account
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Account addAccountWithRollbackForException(Account account) throws Exception {
        Account save = dao.save(account);
        if (true) {
            throw new Exception("抛出异常");
        }
        return save;
    }

    /**
     * 没有指定 回滚的异常，如果抛出指定的异常，就不会回滚
     *
     * @param account
     * @return
     * @throws Exception
     */
    @Transactional
    public Account addAccountWithNoRollbackForException(Account account) throws Exception {
        Account save = dao.save(account);
        if (true) {
            throw new Exception("抛出异常");
        }
        return save;
    }

    // 如果不添加事务，那么B方法的事务也会失效
//    @Transactional(rollbackFor = Exception.class)
    public void methodA() throws Exception {
        System.out.println("methodA");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        account.setName("xxx");
        methodB(account);
    }

    /**
     * 添加事务的方法
     *
     * @param account
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Account methodB(Account account) throws Exception {
        Account save = dao.save(account);
        if (true) {
//            throw new Exception("抛出异常");
        }
        return save;
    }


    /**
     * Propagation.MANDATORY  使用当前的事务，如果当前没有事务，就抛出异常。
     *
     * @param account
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public Account addMantatory(Account account) throws Exception {
        Account save = dao.save(account);
        return save;
    }


    /**
     * Propagation.NEVER  以非事务方式执行，如果当前存在事务，则抛出异常
     *
     * @param account
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NEVER)
    public Account addNEVER(Account account) throws Exception {
        Account save = dao.save(account);
        return save;
    }


}
