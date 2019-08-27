package com.example.xing.testSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiexingxing
 * @Created by 2019-08-23 11:07.
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(888);
        Socket socket = serverSocket.accept();
        OutputStream os = socket.getOutputStream();
        os.write("hellow".getBytes());
        os.close();
        serverSocket.close();
    }
}
