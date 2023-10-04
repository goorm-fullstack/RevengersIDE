package Revengers.IDE.chat.config;

import Revengers.IDE.chat.model.ChatDTO;
import Revengers.IDE.chat.model.ChatRoom;
import Revengers.IDE.chat.repository.ChatMessageRepository;
import Revengers.IDE.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatService service;

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        Map<String, Object> attributes = session.getAttributes();
//        if (attributes.containsKey("loginUser")) {
//            String username = (String) attributes.get("loginUser"); // DB에서 가져온 사용자 아이디 (아직 시큐리티x)
//            attributes.put("username", username);
//        } else {
//            String randomUsername = UUID.randomUUID().toString().substring(0, 8);
//            attributes.put("username", randomUsername);
//        }
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        log.info("session {}", chatMessage.toString());

        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
        log.info("room {}", room.toString());

        room.handleAction(session, chatMessage, service);
    }
}
