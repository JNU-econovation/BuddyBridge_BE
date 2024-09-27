package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.matching.id = :matchingId ORDER BY cm.id DESC")
    List<ChatMessage> findLastMessageByMatchingId(Long matchingId, Pageable pageable);
}
