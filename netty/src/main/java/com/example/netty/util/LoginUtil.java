package com.example.netty.util;

import com.example.netty.attribute.Attributes;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author xiexingxing
 * @Created by 2020-07-15 14:50.
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}