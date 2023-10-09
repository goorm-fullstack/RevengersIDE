package Revengers.IDE.chat.service;

import Revengers.IDE.chat.model.ChatDTO;
import Revengers.IDE.chat.model.ChatMessage;
import Revengers.IDE.chat.model.ChatRoom;
import Revengers.IDE.chat.repository.ChatMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Data
@Service
public class ChatService {
    private final ObjectMapper mapper;
    private Map<String, ChatRoom> chatRooms;
    private ChatRoom chatRoom;
    private final ChatMessageRepository chatMessageRepository;

    @PostConstruct
    private void init() {
        chatRoom = new ChatRoom("RevengersIDE", "Global Chat Room");
    }

    public ChatRoom getChatRoom(){
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            // WebSocket을 통해 메시지 전송
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            log.info("try구문: {}", message);

            // DB에 저장할 메시지 생성
            if (message instanceof ChatDTO chatMessage) {
                log.info("DB에 저장: {}", chatMessage);
                ChatMessage chatMessageEntity = new ChatMessage();
                chatMessageEntity.setMessageType(chatMessage.getType().name());
                chatMessageEntity.setRoomId(chatMessage.getRoomId());
                chatMessageEntity.setSender(chatMessage.getSender());
                chatMessageEntity.setMessage(chatMessage.getMessage());
                chatMessageEntity.setSentAt(LocalDateTime.now());

                chatMessageRepository.save(chatMessageEntity);
            }
        } catch (IOException e) {
            log.error("메세지 저장 실패: " + e.getMessage(), e);
        }
    }
}
