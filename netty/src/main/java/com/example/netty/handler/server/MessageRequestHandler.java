package com.example.netty.handler.server;

import com.example.netty.packet.request.MessageRequestPacket;
import com.example.netty.packet.response.MessageResponsePacket;
import com.example.netty.session.Session;
import com.example.netty.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xiexingxing
 * @Created by 2020-07-15 14:30.
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    // 2. 构造单例
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {


        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
//        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        // 接受消息的人的channel
        Channel channel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if(channel!=null&& SessionUtil.hasLogin(channel)){
            channel.writeAndFlush(messageResponsePacket).addListener( (future)->{
                //这里的 writeAndFlush() 执行完毕之后，并不能代表相关的逻辑，比如事件传播、编码等逻辑执行完毕，只是表示 Netty 接收了这个任务，
                // 那么如何才能判断 writeAndFlush() 执行完毕呢？我们可以这么做 future.isDone
                if (future.isDone()) {
                    System.out.println("future.isDone");
                }
            });
        }else{
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }



    }
}