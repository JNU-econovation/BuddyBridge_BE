package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    ChatMessage findLastMessageByMatchingId(Long matchingId);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.matching.id = :matchingId ORDER BY cm.id ASC")
    Slice<ChatMessage> findByMatchingId(Long matchingId, Pageable pageable);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.matching.id = :matchingId AND cm.id > :cursor ORDER BY cm.id ASC")
    Slice<ChatMessage> findByMatchingIdAndIdGreaterThan(Long matchingId, Long cursor, Pageable pageable);
}
