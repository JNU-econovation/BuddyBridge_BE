package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    ChatMessage findLastMessageByChatRoomId(Long roomId);

    List<ChatMessage> findChatMessagesByChatRoomId(Long chatRoomId);
}
