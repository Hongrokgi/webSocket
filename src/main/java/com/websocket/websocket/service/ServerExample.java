package com.websocket.websocket.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("192.168.0.203",8080));

            while(true) {
                System.out.println("[연결 기다림]");
                Socket socket = serverSocket.accept();
                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
                int receiveBufferSize = socket.getReceiveBufferSize();
                System.out.println("receiveBufferSize = " + receiveBufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }
                br.close();
                System.out.println(sb.toString());

                System.out.println("[연결 수락함]" + isa.getHostName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!serverSocket.isClosed()) {
            try{
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
