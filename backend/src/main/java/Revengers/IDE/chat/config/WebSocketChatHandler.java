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
import org.springframework.web.socket.CloseStatus;
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
    private final String EXIT_MESSAGE = "님이 종료하셨습니다.";

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
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
        System.out.println("call this 2");
        String payload = message.getPayload();
        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        chatMessage.setSender(username);
        ChatRoom room = service.getChatRoom();
        chatMessage.setSender(username);
        room.handleAction(session, chatMessage, service);
    }

    // 클라이언트의 접속이 종료되는 경우
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ChatRoom room = service.getChatRoom();
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
        room.removeSession(session, username+EXIT_MESSAGE, service);//세션 정리
    }
}
