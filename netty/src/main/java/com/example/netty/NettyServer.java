package com.example.netty;


import com.example.netty.codec.PacketCodecHandler;
import com.example.netty.handler.IMIdleStateHandler;
import com.example.netty.handler.server.AuthHandler;
import com.example.netty.handler.server.HeartBeatRequestHandler;
import com.example.netty.handler.server.IMHandler;
import com.example.netty.handler.server.LoginRequestHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author xiexingxing
 * @Created by 2020-04-15 17:20.
 */
public class NettyServer {

    public static void main(String[] args) {
        //们创建了两个NioEventLoopGroup，这两个对象可以看做是传统IO编程模型的两大线程组，
        // bossGroup表示监听端口，accept 新连接的线程组
        // workerGroup表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                //指定我们服务端的 IO 模型为NIO
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //这里主要就是定义后续每条连接的数据读写，业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 获取服务端侧关于这条连接的逻辑处理链 pipeline
                        // 然后addLast添加一个逻辑处理器，负责读取客户端发来的数据
//                        nioSocketChannel.pipeline().addLast(new ServerHandler());

                        //为什么要插入到最前面？是因为如果插入到最后面的话，如果这条连接读到了数据，
                        // 但是在 inBound 传播的过程中出错了或者数据处理完完毕就不往后传递了（我们的应用程序属于这类），那么最终 IMIdleStateHandler 就不会读到数据，最终导致误判
                        nioSocketChannel.pipeline().addLast(new IMIdleStateHandler());
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        //为什么 PacketCodecHandler 这个 handler 可以直接移到前面去，原来的 PacketEncoder 不是在最后吗？
                        //但是随着指令相关的 handler 越来越多，handler 链越来越长，在事件传播过程中性能损耗会被逐渐放大，
                        // 因为解码器解出来的每个 Packet 对象都要在每个 handler 上经过一遍
                        nioSocketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);

                        nioSocketChannel.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);

                        nioSocketChannel.pipeline().addLast(AuthHandler.INSTANCE);
                        //通过压缩成一个handle 处理下面的所有handle，减少handle的数据传递
                        nioSocketChannel.pipeline().addLast(IMHandler.INSTANCE);

//                        nioSocketChannel.pipeline().addLast(MessageRequestHandler.INSTANCE);     //发送单聊
//                        nioSocketChannel.pipeline().addLast(CreateGroupRequestHandler.INSTANCE); //处理创建群消息
//                        nioSocketChannel.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);    // 加入群聊
//                        nioSocketChannel.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE); //群用户列表
//                        nioSocketChannel.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);     //退出群
//                        nioSocketChannel.pipeline().addLast(GroupMessageRequestHandler.INSTANCE); //群里 发消息
//                        nioSocketChannel.pipeline().addLast(LogoutRequestHandler.INSTANCE);  //退出

//                        nioSocketChannel.pipeline().addLast(new PacketEncoder());

                    }
                });
        //绑定端口
        bind(serverBootstrap,8080);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}