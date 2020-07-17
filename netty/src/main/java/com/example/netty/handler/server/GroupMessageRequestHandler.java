package com.example.netty.handler.server;

import com.example.netty.packet.request.GroupMessageRequestPacket;
import com.example.netty.packet.response.GroupMessageResponsePacket;
import com.example.netty.session.Session;
import com.example.netty.util.SessionUtil;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author xiexingxing
 * @Created by 2020-07-16 17:59.
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    // 2. 构造单例
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    protected GroupMessageRequestHandler() {
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {

        String toGroupId = groupMessageRequestPacket.getToGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(toGroupId);
        Session session = SessionUtil.getSession(ctx.channel());

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(toGroupId);
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());
        groupMessageResponsePacket.setFromUser(session);

        channelGroup.writeAndFlush(groupMessageResponsePacket);

    }
}