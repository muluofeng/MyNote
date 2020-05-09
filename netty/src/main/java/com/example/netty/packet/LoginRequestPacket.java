package com.example.netty.packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 10:46.
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return Command.LOGIN_REQUEST;
    }
}