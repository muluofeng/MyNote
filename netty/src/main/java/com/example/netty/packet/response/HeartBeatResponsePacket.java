package com.example.netty.packet.response;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-07-17 15:23.
 */
@Data
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}