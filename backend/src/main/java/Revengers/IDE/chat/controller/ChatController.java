package Revengers.IDE.chat.controller;

import Revengers.IDE.chat.model.ChatRoom;
import Revengers.IDE.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ws")
public class ChatController {
    private final ChatService service;

    // 방 만들기
    @PostMapping("/chat")
    public ChatRoom createRoom(@RequestParam String name){
        return service.createRoom(name);
    }
}
