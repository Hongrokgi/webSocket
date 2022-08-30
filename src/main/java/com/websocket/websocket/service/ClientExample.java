package com.websocket.websocket.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientExample {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket();
            System.out.println("[연결 요청]");
            socket.connect(new InetSocketAddress("192.168.0.203",8080));
            System.out.println("[연결 성공]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!socket.isClosed()) {
            try{
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
