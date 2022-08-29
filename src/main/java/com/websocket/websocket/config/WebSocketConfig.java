package com.websocket.websocket.config;

import com.websocket.websocket.handler.CustomWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
*   앞서 작성한 Handler 를 이용하여 WebSocket 을 활성화하기 위한 Config
*   @EnableWebSocket 선언으로 WebSocket 활성화
*   WebSocket 에 접속하기 위한 endPoint 는 "/ws/chat" 으로 설정, 도메인이 다른 서버에서도 접속가능하도록
*   CORS: setAllowedOrigins("*")
*   클라이언트가 ws://localhost:8070/ws/chat 으로 커넥션을 연결하고 메시지 통신을 할 수 있는 기본적인 준비
* */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final CustomWebSocketHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
