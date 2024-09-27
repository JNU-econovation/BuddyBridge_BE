package econo.buddybridge.chat.chatmessage.repository;

import static econo.buddybridge.chat.chatmessage.entity.QChatMessage.chatMessage;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessagesWithCursor;
import econo.buddybridge.chat.chatmessage.dto.QChatMessageResDto;
import econo.buddybridge.matching.entity.Matching;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ChatMessagesWithCursor findByMatching(Matching matching, Long cursor, Pageable page) {
        int pageSize = page.getPageSize();

        List<ChatMessageResDto> content = queryFactory
                .select(new QChatMessageResDto(
                        chatMessage.id,
                        chatMessage.sender.id,
                        chatMessage.content,
                        chatMessage.messageType,
                        chatMessage.createdAt
                ))
                .from(chatMessage)
                .where(chatMessage.matching.eq(matching), buildCursorPredicate(cursor))
                .orderBy(chatMessage.id.desc())
                .limit(pageSize + 1)
                .fetch();

        boolean nextPage = false;
        if (content.size() > pageSize) {
            content.removeLast();
            nextPage = true;
        }

        Long nextCursor = nextPage ? content.getLast().messageId() : -1L;

        return new ChatMessagesWithCursor(content, nextCursor, nextPage);
    }

    private Predicate buildCursorPredicate(Long cursor) {
        if (cursor == null) {
            return null;
        }

        return chatMessage.id.lt(cursor);
    }
}
