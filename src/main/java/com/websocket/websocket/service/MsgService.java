package com.websocket.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocket.dto.MsgRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/*
*   채팅방을 생성, 조회하고 하나의 세션에 메시지 발송하는 서비스
*   채팅방 Map 은 서버에 생성된 모든 채팅방의 정보를 모아둔 구조체
*   채팅방의 정보저장은 일단은 HashMap 에 저장(추후 DB)
*
*   채팅방 조회 : 채팅방 Map 에 담긴 정보를 조회
*   채팅방 생성 : random UUID 로 구별 ID를 가진 채팅방 객체를 생성하고 채팅방 Map 에 추가
*   추후 MatchId + 진영코드 + 소환사명을 구별 ID로 변경
* */
@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {
    private final ObjectMapper objectMapper;
    private Map<String, MsgRoom> msgRooms;

    @PostConstruct
    private void init() {
        msgRooms = new LinkedHashMap<>();
    }

    public List<MsgRoom> findAllRoom() {
        return new ArrayList<>(msgRooms.values());
    }

    public MsgRoom findById(String roomId) {
        return msgRooms.get(roomId);
    }

    public MsgRoom createRoom(String name) {
        //TODO : roomId == Summoner MatchId + blue/red Team Code + Summoner Name
        String roomId = name;
        MsgRoom room = MsgRoom.builder()
                .roomId(roomId)
                .build();
        msgRooms.put(room.getRoomId(),room);
        return room;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message))); //writeValueAsString -> unhandled exception
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
