package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.matching.id = :matchingId ORDER BY cm.id DESC")
    List<ChatMessage> findLastMessageByMatchingId(Long matchingId, Pageable pageable);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.matching.id = :matchingId ORDER BY cm.id DESC")
    Slice<ChatMessage> findByMatchingId(Long matchingId, Pageable pageable);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.matching.id = :matchingId AND cm.id > :cursor ORDER BY cm.id DESC")
    Slice<ChatMessage> findByMatchingIdAndIdGreaterThan(Long matchingId, Long cursor, Pageable pageable);
}
