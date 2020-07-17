package com.example.netty;

import com.example.netty.packet.request.LoginRequestPacket;
import com.example.netty.packet.response.LoginResponsePacket;
import com.example.netty.packet.Packet;
import com.example.netty.packet.PacketCode;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 11:06.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 这个逻辑处理器继承自 ChannelInboundHandlerAdapter，
     * 然后覆盖了 channelActive()方法，这个方法会在客户端连接建立成功之后被调用
     *
     *  写数据的逻辑分为两步
     *      1.首先我们需要获取一个 netty 对二进制数据的抽象 ByteBuf
     *      2.最后我们调用 ctx.channel().writeAndFlush() 把数据写到服务端
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");



        // 编码
        ByteBuf buffer = PacketCode.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写数据
        ctx.channel().writeAndFlush(buffer);
        System.out.println("客户端发送消息："+loginRequestPacket.toString());


        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = getByteBuf(ctx);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCode.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            System.out.println("客户端收到消息："+loginResponsePacket.toString());

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
    }
}