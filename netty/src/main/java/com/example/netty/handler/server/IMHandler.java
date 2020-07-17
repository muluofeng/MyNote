package com.example.netty.handler.server;

import com.example.netty.packet.Command;
import com.example.netty.packet.Packet;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xiexingxing
 * @Created by 2020-07-17 11:02.
 */
@ChannelHandler.Sharable
public class IMHandler  extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    //我们定义一个 map，存放指令到各个指令处理器的映射。
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        //每次回调到 IMHandler 的 channelRead0() 方法的时候，我们通过指令找到具体的 handler，
        // 然后调用指令 handler 的 channelRead，他内部会做指令类型转换，最终调用到每个指令 handler 的 channelRead0() 方法。
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}