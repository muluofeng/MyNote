package com.example.netty.packet;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 10:46.
 */
public interface Command {
    //登录请求
    Byte LOGIN_REQUEST = 1;
    //登录响应
    Byte LOGIN_RESPONSE = 2;
    //消息请求
    Byte MESSAGE_REQUEST = 3;
    //消息响应
    Byte MESSAGE_RESPONSE = 4;
    //退出请求
    Byte LOGOUT_REQUEST = 5;
    //退出响应
    Byte LOGOUT_RESPONSE = 6;

    Byte CREATE_GROUP_REQUEST = 7;

    Byte CREATE_GROUP_RESPONSE = 8;

    Byte LIST_GROUP_MEMBERS_REQUEST = 9;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;

    Byte JOIN_GROUP_REQUEST = 11;

    Byte JOIN_GROUP_RESPONSE = 12;

    Byte QUIT_GROUP_REQUEST = 13;

    Byte QUIT_GROUP_RESPONSE = 14;

    Byte GROUP_MESSAGE_REQUEST = 15;

    Byte GROUP_MESSAGE_RESPONSE = 16;


    Byte HEARTBEAT_REQUEST = 17;

    Byte HEARTBEAT_RESPONSE = 18;
}
