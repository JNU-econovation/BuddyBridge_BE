package econo.buddybridge.chat.chatroom.entity;


import econo.buddybridge.common.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="CHAT_ROOM")
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomState roomState;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    @Builder
    public ChatRoom(RoomState roomState, String lastMessage, LocalDateTime lastMessageTime){
        this.roomState = roomState;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public void updateChatRoom(RoomState roomState,String lastMessage,LocalDateTime lastMessageTime){
        this.roomState = roomState;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}
