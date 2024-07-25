package econo.buddybridge.matching.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.MatchingResDto;
import econo.buddybridge.matching.dto.ReceiverDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static econo.buddybridge.chat.chatmessage.entity.QChatMessage.chatMessage;
import static econo.buddybridge.matching.entity.QMatching.matching;
import static econo.buddybridge.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryCustomImpl implements MatchingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // TODO : 전체 매칭 리스트를 가져와
    // 가져오는데 커서기반 페이지네이션을 적용
    // cursor가 있는지 없는지에 따라
    // matchingStatus가 있는지 없는지 따라
    // 채팅의 마지막 생성 시간을 가져와서 정렬
    @Override
    public MatchingCustomPage findMatchings(Long memberId, Integer size, LocalDateTime cursor, MatchingStatus matchingStatus, Pageable page) {
        int pageSize = page.getPageSize();

        List<Matching> matchings = queryFactory
                .selectFrom(matching)
                .where(
                        matching.taker.id.eq(memberId).or(matching.giver.id.eq(memberId)),
                        buildCursorExpression(cursor),
                        buildMatchingStatusExpression(matchingStatus)
                )
                .limit(size + 1)
                .fetch();

        List<MatchingResDto> matchingResDtos = matchings.stream()
                .map(matching1 -> {
                    ChatMessage lastMessage = queryFactory
                            .selectFrom(chatMessage)
                            .where(chatMessage.matching.id.eq(matching1.getId()))
                            .orderBy(chatMessage.createdAt.desc())
                            .limit(1)
                            .fetchOne();

                    Long receiverId = memberId.equals(matching1.getTaker().getId()) ? matching1.getGiver().getId() : matching1.getTaker().getId();
                    Member receiver = queryFactory
                            .selectFrom(member)
                            .where(member.id.eq(receiverId))
                            .fetchOne();

                    return new MatchingResDto(
                            matching1.getId(),
                            matching1.getPost().getPostType(),
                            matching1.getPost().getId(),
                            lastMessage.getContent(),
                            lastMessage.getCreatedAt(),
                            lastMessage.getMessageType(),
                            matching1.getMatchingStatus(),
                            new ReceiverDto(receiver)
                    );
                }).toList();

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