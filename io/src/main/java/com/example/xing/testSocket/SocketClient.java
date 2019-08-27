package com.example.xing.testSocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author xiexingxing
 * @Created by 2019-08-23 11:06.
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 888);
        InputStream is = socket.getInputStream();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        is.close();
        socket.close();
    }
}
