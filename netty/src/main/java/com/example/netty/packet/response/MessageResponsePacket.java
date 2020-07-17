package com.example.netty.packet.response;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-07-15 14:35.
 */
@Data
public class MessageResponsePacket extends Packet {


    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return Command.MESSAGE_RESPONSE;
    }
}