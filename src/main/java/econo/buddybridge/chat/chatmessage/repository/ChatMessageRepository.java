package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.matching.entity.Matching;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {

    Iterable<ChatMessage> findByMatchingIn(List<Matching> matchings);
}
