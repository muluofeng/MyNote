package com.example.netty.attribute;

import com.example.netty.session.Session;

import io.netty.util.AttributeKey;

/**
 * @author xiexingxing
 * @Created by 2020-07-15 14:50.
 */
public interface Attributes {
     AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
     AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}