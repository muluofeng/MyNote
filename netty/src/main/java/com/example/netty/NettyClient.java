package com.example.netty;

import com.example.netty.console.ConsoleCommandManager;
import com.example.netty.console.LoginConsoleCommand;
import com.example.netty.handler.IMIdleStateHandler;
import com.example.netty.handler.client.CreateGroupResponseHandler;
import com.example.netty.handler.client.GroupMessageResponseHandler;
import com.example.netty.handler.client.HeartBeatTimerHandler;
import com.example.netty.handler.client.JoinGroupResponseHandler;
import com.example.netty.handler.client.ListGroupMembersResponseHandler;
import com.example.netty.handler.client.LoginResponseHandler;
import com.example.netty.handler.client.MessageResponseHandler;
import com.example.netty.handler.client.QuitGroupResponseHandler;
import com.example.netty.packet.PacketDecoder;
import com.example.netty.packet.PacketEncoder;
import com.example.netty.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xiexingxing
 * @Created by 2020-04-15 18:11.
 */
public class NettyClient {
    public static final int MAX_RETRY =5;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sch) throws Exception {
                        //sch.pipeline() 返回的是和这条连接相关的逻辑处理链，采用了责任链模式
                        //然后再调用 addLast() 方法 添加一个逻辑处理器，这个逻辑处理器为的就是在客户端建立连接成功之后，向服务端写数据
//                        sch.pipeline().addLast(new ClientHandler());

                        sch.pipeline().addLast(new IMIdleStateHandler());  // 空闲检测
                        sch.pipeline().addLast(new Spliter());
                        sch.pipeline().addLast(new PacketDecoder());
                        sch.pipeline().addLast(new LoginResponseHandler());
                        sch.pipeline().addLast(new MessageResponseHandler());
                        sch.pipeline().addLast(new CreateGroupResponseHandler());
                        sch.pipeline().addLast(new JoinGroupResponseHandler());
                        sch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        sch.pipeline().addLast(new QuitGroupResponseHandler());
                        sch.pipeline().addLast(new GroupMessageResponseHandler());
                        sch.pipeline().addLast(new PacketEncoder());

                        // 心跳定时器
                        sch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });

        connect(bootstrap,"localhost",8080,5);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");


                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);


            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if(!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(scanner, channel);
                }else{
                   consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}