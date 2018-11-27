package com.example.xing.security;

import com.example.xing.model.User;

/**
 * @author xiexingxing
 * @Created by 2018-11-24 6:07 PM.
 */
public interface AuthService {
    /**
     * 注册一个用户
     * @param userToAdd
     * @return
     */
    User register(User userToAdd);

    /**
     * 登录
     */
    String login(String username, String password);

    /**
     * 刷新
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);
}
