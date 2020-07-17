package com.example.netty.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiexingxing
 * @Created by 2020-07-16 10:03.
 */
@Data
@NoArgsConstructor
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}