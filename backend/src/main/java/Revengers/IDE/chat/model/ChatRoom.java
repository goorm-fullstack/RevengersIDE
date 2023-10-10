package Revengers.IDE.chat.model;

import Revengers.IDE.chat.service.ChatService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@Slf4j
public class ChatRoom {
    private String roomId; // 채팅방 아이디
    private String name; // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatDTO message, ChatService service) {
        try {
            log.info("handleAction try구문 실행");
            sessions.add(session);
            message.setRoomId(this.roomId);
            log.info("현재 세션 수: " + sessions.size());
            // ChatDTO message에서 getType 확인 -> 일치하면 세션+, 아니면 메세지만
            if (message.getType().equals(ChatDTO.MessageType.ENTER)) {
                message.setMessage(message.getSender() + " 님이 입장하셨습니다");
                message.setSender("Notice");
                sendMessage(message, service);

            } else if (message.getType().equals(ChatDTO.MessageType.TALK)) {
                message.setMessage(message.getMessage());
                sendMessage(message, service);
            } else {
                message.setMessage(message.getSender()+"님이 퇴장하셨습니다.");
                sendMessage(message, service);
            }
        } catch (Exception e) {
            log.error("handleAction 에러 발생", e);
        }
    }

    public <T> void sendMessage(T message, ChatService service) {
        sessions.forEach(session -> {
            try {
                // 세션이 열려 있는 경우에만 메시지 전송
                if (session.isOpen()) {
                    service.sendMessage(session, message);
                }
            } catch (Exception e) {
                log.error("Error sending message to session", e);
            }
        });
    }

    public <T> void sendExitedMessage(T message, ChatService service) {
        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }

    public void removeSession(WebSocketSession session, String message, ChatService service) {
        log.info("Entering removeSession method");
        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession currentSession = iterator.next();
            if (currentSession.equals(session)) {
                iterator.remove();
                log.info("Session removed successfully");
            }
        }
        for (WebSocketSession webSocketSession : sessions) {
            log.info("Preparing to send exited message");
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setRoomId(roomId);
            chatMessage.setSender("Notice");
            chatMessage.setMessage(message);
            service.sendExitedMessage(webSocketSession, chatMessage);
            log.info("Exited message sent successfully");
        }
        log.info("Exiting removeSession method");
    }

}
