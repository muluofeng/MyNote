package com.example.netty.console;

import com.example.netty.packet.request.LogoutRequestPacket;

import java.util.Scanner;

import io.netty.channel.Channel;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
