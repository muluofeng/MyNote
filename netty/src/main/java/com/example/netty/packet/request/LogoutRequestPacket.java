package com.example.netty.packet.request;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-07-16 15:26.
 */
@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}