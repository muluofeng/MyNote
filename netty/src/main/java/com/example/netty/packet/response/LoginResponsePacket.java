package com.example.netty.packet.response;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 11:19.
 */
@Data
public class LoginResponsePacket extends Packet {


    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

    @Override
    public String toString() {
        return "LoginResponsePacket{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", success=" + success +
                ", reason='" + reason + '\'' +
                '}';
    }
}