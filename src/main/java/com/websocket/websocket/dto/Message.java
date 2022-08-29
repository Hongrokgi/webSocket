package com.websocket.websocket.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/*
*  채팅 메시지를 주고받기 위한 DTO
*  채팅방 입장, 채팅방에 메시지 보내기 두 가지 상황에 맞춰서 enum 구현
*  방번호, 보내는 이, 내용 포함
* */
@Getter
@Setter
public class Message {
    public enum MessageType {
        ENTER, COMM
    }
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
