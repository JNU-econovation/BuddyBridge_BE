package econo.buddybridge.chat.chatroom.repository;

import econo.buddybridge.chat.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

}
