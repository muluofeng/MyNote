package com.example.netty.packet;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 10:44.
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
