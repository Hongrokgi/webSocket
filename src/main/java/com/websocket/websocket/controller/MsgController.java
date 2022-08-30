package com.websocket.websocket.controller;

import com.websocket.websocket.dto.MsgRoom;
import com.websocket.websocket.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
*   채팅방의 생성 및 조회
* */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MsgController {
    private final MsgService msgService;

    @PostMapping
    public MsgRoom createRoom(@RequestParam String name) {
        //TODO: name is Summoner Name
        return msgService.createRoom(name);
    }

    @GetMapping
    public List<MsgRoom> findAllRoom() {
        return msgService.findAllRoom();
    }


}
