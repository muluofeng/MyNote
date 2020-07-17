package com.example.netty.packet.request;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import lombok.Data;

@Data
public class ListGroupMembersRequestPacket extends Packet
{

    private String groupId;

    @Override
    public Byte getCommand() {

        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
