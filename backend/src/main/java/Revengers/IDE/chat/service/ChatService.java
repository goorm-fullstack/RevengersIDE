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
import java.util.*;

@Slf4j
@Data
@Service
public class ChatService {
    private final ObjectMapper mapper;
    private Map<String, ChatRoom> chatRooms;
    private final ChatMessageRepository chatMessageRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId){
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String roomId = UUID.randomUUID().toString(); // 랜덤한 방 아이디 생성

        // Builder 를 이용해서 ChatRoom 을 Building
        ChatRoom room = ChatRoom.builder()
                .roomId(roomId)
                .name(name)
                .build();

        chatRooms.put(roomId, room); // 랜덤 아이디와 room 정보를 Map 에 저장
        return room;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            // WebSocket을 통해 메시지 전송
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));

            // DB에 저장할 메시지 생성
            if (message instanceof ChatDTO chatMessage) {
                // ChatDTO chatMessage = (ChatDTO) message; 를 instanceof로 변경 (오류시 삭제)
                ChatMessage chatMessageEntity = new ChatMessage();
                chatMessageEntity.setMessageType(chatMessage.getType().name());
                chatMessageEntity.setRoomId(chatMessage.getRoomId());
                chatMessageEntity.setSender(chatMessage.getSender());
                chatMessageEntity.setMessage(chatMessage.getMessage());
                chatMessageEntity.setSentAt(chatMessage.getTime());

                chatMessageRepository.save(chatMessageEntity);
            }
        } catch (IOException e) {
            log.error("메세지 저장 실패: " + e.getMessage(), e);
        }
    }
}
