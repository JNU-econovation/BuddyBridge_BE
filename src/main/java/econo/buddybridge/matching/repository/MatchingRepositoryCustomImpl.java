package econo.buddybridge.matching.repository;

import static econo.buddybridge.chat.chatmessage.entity.QChatMessage.chatMessage;
import static econo.buddybridge.matching.entity.QMatching.matching;
import static econo.buddybridge.member.entity.QMember.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.chat.chatmessage.entity.QChatMessage;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.MatchingResDto;
import econo.buddybridge.matching.dto.QMatchingResDto;
import econo.buddybridge.matching.dto.QReceiverDto;
import econo.buddybridge.matching.entity.MatchingStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryCustomImpl implements MatchingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MatchingCustomPage findMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus, Pageable page) {
        int pageSize = page.getPageSize();

        QChatMessage subChatMessage = new QChatMessage("subChatMessage");

        List<MatchingResDto> matchingResDtos = queryFactory
                .select(new QMatchingResDto(
                        matching.id,
                        matching.post.postType,
                        matching.post.id,
                        chatMessage.content,
                        chatMessage.createdAt,
                        chatMessage.messageType,
                        matching.matchingStatus,
                        new QReceiverDto(
                                member.id,
                                member.name,
                                member.profileImageUrl
                        )
                ))
                .from(matching)
                .leftJoin(chatMessage).on(chatMessage.matching.eq(matching)
                        .and(chatMessage.id.eq(JPAExpressions
                                .select(subChatMessage.id.max())
                                .from(subChatMessage)
                                .where(subChatMessage.matching.eq(matching)))))
                .leftJoin(member).on(
                        member.id.eq(
                                new CaseBuilder()
                                        .when(matching.taker.id.eq(memberId)).then(matching.giver.id)
                                        .otherwise(matching.taker.id)
                        )
                )
                .where(
                        matching.taker.id.eq(memberId).or(matching.giver.id.eq(memberId)),
                        buildCursorExpression(cursor),
                        buildMatchingStatusExpression(matchingStatus)
                )
                .orderBy(chatMessage.createdAt.desc())
                .limit(size + 1)
                .fetch();

        boolean nextPage = false;
        if (matchingResDtos.size() > pageSize) {
            matchingResDtos.removeLast();
            nextPage = true;
        }

        LocalDateTime nextCursor = nextPage ? matchingResDtos.getLast().lastMessageTime() : LocalDateTime.MIN;

        return new MatchingCustomPage(matchingResDtos, nextCursor, nextPage);
    }

    private BooleanExpression buildCursorExpression(LocalDateTime cursor) {
        return cursor == null ? null : chatMessage.createdAt.lt(cursor);
    }

    private BooleanExpression buildMatchingStatusExpression(MatchingStatus matchingStatus) {
        return matchingStatus == null ? null : matching.matchingStatus.eq(matchingStatus);
    }
}