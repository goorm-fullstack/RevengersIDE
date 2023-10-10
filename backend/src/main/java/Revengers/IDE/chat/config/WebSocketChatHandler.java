package Revengers.IDE.chat.config;

import Revengers.IDE.chat.model.ChatDTO;
import Revengers.IDE.chat.model.ChatRoom;
import Revengers.IDE.chat.service.ChatService;
import Revengers.IDE.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        SecurityContext securityContext = (SecurityContext) attributes.get("SPRING_SECURITY_CONTEXT"); // Spring Security context를 가져옴

        String username;
        if (securityContext != null) {
            Authentication auth = securityContext.getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                username = auth.getName();
            } else {
                username = UUID.randomUUID().toString().substring(0, 8);
            }
        } else {
            username = UUID.randomUUID().toString().substring(0, 8);
        }

        attributes.put("username", username);

        ChatDTO initialMessage = ChatDTO.builder()
                .type(ChatDTO.MessageType.ENTER)
                .sender("SERVER")
                .message(username)
                .build();

        session.sendMessage(new TextMessage(mapper.writeValueAsString(initialMessage)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        log.info("session {}", chatMessage.toString());

        ChatRoom room = service.getChatRoom();
        log.info("room {}", room.toString());

        room.handleAction(session, chatMessage, service);
        log.info("핸들액션={}, {}, {}" + session, chatMessage, service);
    }
}
