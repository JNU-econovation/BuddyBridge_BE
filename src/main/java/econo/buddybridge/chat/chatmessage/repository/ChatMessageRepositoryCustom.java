package econo.buddybridge.chat.chatmessage.repository;

import econo.buddybridge.chat.chatmessage.dto.ChatMessagesWithCursor;
import econo.buddybridge.matching.entity.Matching;
import org.springframework.data.domain.Pageable;

public interface ChatMessageRepositoryCustom {

    ChatMessagesWithCursor findByMatching(Matching matching, Long cursor, Pageable page);
}
