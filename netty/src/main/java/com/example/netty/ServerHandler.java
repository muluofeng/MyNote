package com.example.netty;

import com.example.netty.packet.request.LoginRequestPacket;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 11:15.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 服务端侧的逻辑处理器同样继承自 ChannelInboundHandlerAdapter，
     * 与客户端不同的是，这里覆盖的方法是 channelRead()，这个方法在接收到客户端发来的数据之后被回调
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        System.out.println( requestByteBuf.toString(Charset.forName("utf-8")));

        return;
/*        // 解码
        Packet packet = PacketCode.INSTANCE.decode(requestByteBuf);



        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            System.out.println("服务器端收到消息："+loginRequestPacket.toString());

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            // 登录校验
            if (valid(loginRequestPacket)) {
                // 校验成功
                loginResponsePacket.setSuccess(true);
            } else {
                // 校验失败
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
            }

            *//**
             *  服务端向客户端写数据逻辑与客户端侧的写数据逻辑一样
             *  1. 先创建一个 ByteBuf，然后填充二进制数据，
             *  2. 最后调用 writeAndFlush() 方法写出去
             *//*


            ByteBuf responseByteBuf = PacketCode.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);

            System.out.println("服务器端发送消息："+loginResponsePacket.toString());


        }*/
    }



    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}