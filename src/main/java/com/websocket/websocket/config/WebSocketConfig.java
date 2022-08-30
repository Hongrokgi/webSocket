package com.websocket.websocket.config;

import com.websocket.websocket.handler.CustomWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/*
*   앞서 작성한 Handler 를 이용하여 WebSocket 을 활성화하기 위한 Config
*   @EnableWebSocket 선언으로 WebSocket 활성화
*   WebSocket 에 접속하기 위한 endPoint 는 "/ws/chat" 으로 설정, 도메인이 다른 서버에서도 접속가능하도록
*   CORS: setAllowedOrigins("*")
*   클라이언트가 ws://localhost:8070/ws/chat 으로 커넥션을 연결하고 메시지 통신을 할 수 있는 기본적인 준비
*
*   0830 STOMP: Simple Text Oriented Messaging Protocol
*   stomp : 메시지 전송을 효율적으로 하기 위한 프로토콜, 기본적으로 PUB/SUB 구조. 메시지를 전송하고 / 받아서 처리하는 부분이
*   확실하게 구조로 정해져있기 때문에 명확하게 인지하고 개발할 수 있다.
*   STOMP 프로토콜은 클라이언트/서버 간 전송할 메시지의 유형, 형식, 내용들을 정의한 규칙.
*   TCP 또는 WebSocket 과 같은 양방향 네트워크 프로토콜 기반으로 동작
*   헤더에 값을 세팅할 수 있어. 헤더 값을 기반으로 통신 시 인증처리를 구현할 수 있다.
*
*   PUB/SUB
*   메시지를 공급하는 주체와 소비하는 주체를 분리해 제공하는 메시징 방법. 채팅방을 생성하는 것은 우체통 Topic 을 만드는 것.
*   채팅방에 들어가는 것은 구독자로서 Subscriber 가 되는 것. 채팅방에 글을 써서 보내는 행위는 Publisher 가 됨
*
*   Message Brocker
*   Message Brocker : Publisher 로 부터 전달받은 메시지를 Subscriber 에게 메시지를 주고 받게 해주는 중간 역할.
*   클라이언트는 SEND, SUBSCRIBE 명령을 통해서 메시지의 내용과 수신 대상을 설명하는 destination 헤더와 함께 메시지에 대한
*   전송이나 구독을 할 수 있다. 이것이 브로커를 통해 연결된 다른 클라이언트로 메시지를 보내거나, 서버로 메시지를 보내 일부 작업을
*   요청할 수 있는 PUB/SUB 메커니즘을 가능케 함.
*
*   스프링이 지원하는 STOMP 에서는 스프링 웹 소켓 애플리케이션이 클라이언트에게 STOMP 브로커의 역할 이때 메시지는 @Controller 메시지 처리 방법이나
*   Subscriber 를 추적해서 구독중인 사용자에게 메시지를 전파(BroadCase) 하는 Simple in Memory 브로커에게 라우팅
*   이렇게 Spring 환경에서 추가적인 설정없이 STOMP 프로토콜을 사용하면 메시지 브로커는 자동으로 In Memory Brocker 사용
*   1. 세션을 수용할 수 있는 크기 제한
*   2. 장애 발생 시 메시지의 유실 가능성이 높음
*   3. 따로 모니터링 하는 것이 불편
*
*   따라서 STOMP 전용 외부 브로커 사용을 지향. RabbitMQ, ActiveMQ(초당 5000~10000 메시지 발생되는 규모모)
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocket //STOMP 사용
public class WebSocketConfig implements WebSocketConfigurer {
    private final CustomWebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
