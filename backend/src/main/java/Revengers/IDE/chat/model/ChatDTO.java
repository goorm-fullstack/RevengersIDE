package Revengers.IDE.chat.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatDTO {
    // 첫 입장 메세지, 이후 메세지
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type; // 타입
    private String roomId; // 방 번호
    private String sender; // 채팅 보낸사람
    private String message; // 채팅 메시지
    private LocalDateTime time; // 채팅 발송시간
}
