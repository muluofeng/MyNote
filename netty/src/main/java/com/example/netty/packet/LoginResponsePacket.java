package com.example.netty.packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 11:19.
 */
@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}