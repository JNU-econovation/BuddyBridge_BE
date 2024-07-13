package econo.buddybridge.chat.chatroom.entity;


import econo.buddybridge.common.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TODO: 채팅방 -> 매칭으로 통합
@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="CHAT_ROOM")
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastMessage;
    private LocalDateTime lastMessageTime;

    @Builder
    public ChatRoom(String lastMessage, LocalDateTime lastMessageTime){
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    // 채팅 메시지 보낼때 자동 업데이트 적용
    public void updateChatRoom(String lastMessage,LocalDateTime lastMessageTime){
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}

