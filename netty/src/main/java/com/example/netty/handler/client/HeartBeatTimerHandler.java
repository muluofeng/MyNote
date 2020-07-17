package com.example.netty.handler.client;

import com.example.netty.packet.request.HeartBeatRequestPacket;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xiexingxing
 * @Created by 2020-07-17 15:20.
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        /**
         * ctx.executor() 返回的是当前的 channel 绑定的 NIO 线程，不理解没关系，只要记住就行，
         * 然后，NIO 线程有一个方法，schedule()，类似 jdk 的延时任务机制，可以隔一段时间之后执行一个任务，
         * 而我们这边是实现了每隔 5 秒，向服务端发送一个心跳数据包，这个时间段通常要比服务端的空闲检测时间的一半要短一些，
         * 我们这里直接定义为空闲检测时间的三分之一，主要是为了排除公网偶发的秒级抖动
         *
         * 实际在生产环境中，我们的发送心跳间隔时间和空闲检测时间可以略长一些，可以设置为几分钟级别，具体应用可以具体对待，没有强制的规定
         */
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                System.out.println("client 发送心跳检测");
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }

        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}