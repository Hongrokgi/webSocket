package com.websocket.websocket.dto;

import com.websocket.websocket.service.MsgService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
/*
*  채팅방은 현재 방에 입장한 클라이언트의 Session 정보를 가져야 한다.
*  채팅방 id(== 추후 MatchId + 진영 코드 + 소환사 명을 구별 ID로 한다)
*  채팅방에는 입장/통신 기능이 있으므로 handleAction 을 통해 분기 처리
*  입장시에는 채팅방의 session 정보 리스트에 클라이언트의 session 을 추가해놓고, 채팅방에 메시지가 도착할 경우 채팅방의
*  모든 session 에 메시지를 발송하면 된다.
*  type 이 ENTER 일 경우에는 입장한 클라이언트의 웹 소켓 세션을 채팅창 ID 내 Set<> 에 저장하고, COMM 인 경우에는 바로 message 내용만 출력
*  이 때 message 는 채팅방 Set<> 에 존재하는 모든 세션에 보낸다.
* */
@Getter
@Setter
public class MsgRoom {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public MsgRoom(String roomId) {
        this.roomId=roomId;
    }

    public void handleActions(WebSocketSession session, Message message, MsgService msgService) {
        if(message.getMessageType().equals(Message.MessageType.ENTER)) {
            sessions.add(session);
            message.setMessage(message.getSender()+ "님이 입장했습니다.");
        }
        sendMessage(message, msgService);
    }

    public <T> void sendMessage(T message, MsgService messageService) {
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session, message));
    }
}
