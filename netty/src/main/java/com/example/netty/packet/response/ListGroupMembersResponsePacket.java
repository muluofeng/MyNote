package com.example.netty.packet.response;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;
import com.example.netty.session.Session;

import java.util.List;

import lombok.Data;


@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
