package com.imooc.netty.chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author xuanjian
 */
public class ClientHandler {

    private static final int MAX_DATA_LENGTH = 1024;
    private Socket socket;


    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        System.out.println("新客户端接入");
        new Thread(() -> doStart()).start();
    }

    private void doStart() {
        try {
            InputStream in = socket.getInputStream();
            while (true) {
                byte[] data = new byte[MAX_DATA_LENGTH];
                int len;
                while (((len = in.read(data)) != -1)) {
                    String message = new String(data, 0, len);
                    System.out.println("客户端传来消息: " + message);
                    socket.getOutputStream().write(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

