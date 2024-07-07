package econo.buddybridge.chat.chatmessage.entity;

import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "CHAT_MESSAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Builder
    public ChatMessage(ChatRoom chatRoom, Member sender, String content, MessageType messageType){
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.messageType = messageType;
    }

    public void updateChatMessage(String content,MessageType messageType){
        this.content = content;
        this.messageType = messageType;
    }

    public void deleteChatMessage(ChatMessage chatMessage){
        chatMessage.messageType = MessageType.DELETE;
    }
ㅎ
}
