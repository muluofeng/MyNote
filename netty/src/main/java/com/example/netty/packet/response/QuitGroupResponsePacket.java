package com.example.netty.packet.response;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return Command.QUIT_GROUP_RESPONSE;
    }
}
