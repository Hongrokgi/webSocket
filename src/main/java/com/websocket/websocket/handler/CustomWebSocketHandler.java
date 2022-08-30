package com.websocket.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocket.dto.Message;
import com.websocket.websocket.dto.MsgRoom;
import com.websocket.websocket.service.MsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
/*
*   소켓 통신은 기본적으로 서버와 클라이언트간에 1:N 관계를 맺는다.
*   따라서 한 서버에 여러 클라이언트가 접속할 수 있으므로, 서버는 여러 클라이언트가 발송한 메시지를 받아서 처리해줄 Handler 필요
*   TextWebSocketHandler 를 상속받아 Handler 작성
* */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private final MsgService msgService;
    private final ObjectMapper objectMapper;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

//        TextMessage initialGreeting = new TextMessage("Welcome to Rokgi Chat Server ~O_O~");
//        session.sendMessage(initialGreeting);
        // 현재 Handler -> Client 에게 받은 메시지를 log 출력하고 Client 에게 환영 메시지를 리턴.
        // 클라이언트들은 서버에 접속하면 개별의 WebSocket Session 을 갖는다. 따라서 채팅방에 입장하면 클라이언트들의 WebSocket Session 정보를 채팅방에
        // 매핑해서 보관하고 있으면, 서버에 전달된 메시지를 특정 방에 매핑된 webSocket 세션 리스트에 보낼 수 있으므로 개별의 채팅방 구현이 가능하다.
        Message msg = objectMapper.readValue(payload, Message.class);
        MsgRoom room = msgService.findById(msg.getRoomId());
        room.handleActions(session, msg, msgService);
    }
}
