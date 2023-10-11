package Revengers.IDE.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    // 첫 입장 메세지, 이후 메세지
    public enum MessageType{
        ENTER, TALK, SESSION_COUNT
    }

    private MessageType type; // 타입
    private String roomId; // 방 번호
    private String sender; // 채팅 보낸사람
    private String message; // 채팅 메시지
    private LocalDateTime time; // 채팅 발송시간
}
