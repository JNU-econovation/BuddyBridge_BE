package econo.buddybridge.matching.repository;

import static econo.buddybridge.chat.chatmessage.entity.QChatMessage.chatMessage;
import static econo.buddybridge.chat.chatmessage.entity.QMessageReadStatus.messageReadStatus;
import static econo.buddybridge.matching.entity.QMatching.matching;
import static econo.buddybridge.member.entity.QMember.member;
import static econo.buddybridge.post.entity.QPost.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.chat.chatmessage.entity.QChatMessage;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.dto.MatchingResDto;
import econo.buddybridge.matching.dto.QMatchingResDto;
import econo.buddybridge.matching.dto.QReceiverDto;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.post.dto.CompletedVolunteerPostDto;
import econo.buddybridge.post.dto.CompletedVolunteerPostPage;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.dto.QCompletedVolunteerPostDto;
import econo.buddybridge.post.dto.QCompletedVolunteerScheduleDto;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.QPost;
import econo.buddybridge.post.exception.PostInvalidSortValueException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryImpl implements MatchingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MatchingCustomPage findMatchings(Long memberId, Integer size, LocalDateTime cursor, List<MatchingStatus> matchingStatus, Pageable page) {
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
                        ),
                        JPAExpressions
                                .select(subChatMessage.id.count())          // 읽지 않은 메시지 수
                                .from(subChatMessage)
                                .where(subChatMessage.matching.eq(matching)         // 해당 매칭방의 메시지 중
                                        .and(subChatMessage.sender.id.ne(memberId)) // 내가 보낸 메시지는 제외하고
                                        .and(subChatMessage.createdAt.gt(           // 마지막 읽은 시간 이후 메시지
                                                JPAExpressions
                                                        .select(messageReadStatus.lastReadTime)
                                                        .from(messageReadStatus)
                                                        .where(messageReadStatus.reader.id.eq(memberId)
                                                                .and(messageReadStatus.matching.eq(matching)))
                                        ))
                                )
                ))
                .from(matching)
                .leftJoin(chatMessage).on(chatMessage.matching.eq(matching) // 마지막 메시지 정보
                        .and(chatMessage.id.eq(
                                JPAExpressions
                                        .select(subChatMessage.id.max())
                                        .from(subChatMessage)
                                        .where(subChatMessage.matching.eq(matching)))
                        ))
                .leftJoin(member).on(   // 상대방 정보
                        member.id.eq(
                                new CaseBuilder()
                                        .when(matching.taker.id.eq(memberId)).then(matching.giver.id)
                                        .otherwise(matching.taker.id)
                        )
                )
                .where(
                        matching.taker.id.eq(memberId).or(matching.giver.id.eq(memberId)),  // 내가 참여한 매칭
                        buildCursorExpression(cursor),
                        buildMatchingStatusExpression(matchingStatus)
                )
                .orderBy(chatMessage.createdAt.desc())  // 최신 메시지 순
                .limit(size + 1L)
                .fetch();

        boolean nextPage = false;
        if (matchingResDtos.size() > pageSize) {
            matchingResDtos.removeLast();
            nextPage = true;
        }

        LocalDateTime nextCursor = nextPage ? matchingResDtos.getLast().lastMessageTime() : LocalDateTime.MIN;

        return new MatchingCustomPage(matchingResDtos, nextCursor, nextPage);
    }

    @Override
    public boolean existsCompletedMatchingByPost(Post post) {
        return queryFactory
                .selectOne()
                .from(matching)
                .where(
                        matching.post.eq(post),
                        matching.matchingStatus.in(MatchingStatus.getCompletedStatuses())
                )
                .fetchFirst() != null;
    }

    @Override
    public CompletedVolunteerPostPage getCompletedVolunteerPosts(Member author, Integer page, Integer size, String sort, MemberRole memberRole, Boolean isCompleted) {

        List<CompletedVolunteerPostDto> content = queryFactory
                .select(new QCompletedVolunteerPostDto(
                        matching.post.id,
                        matching.id,
                        matching.giver.name,
                        matching.giver.email,
                        matching.post.title,
                        matching.post.postType,
                        Expressions.constant(PostStatus.FINISHED),
                        matching.post.district,
                        matching.post.disabilityType,
                        matching.post.assistanceType,
                        new QCompletedVolunteerScheduleDto(
                                post.schedule.startDate,
                                post.schedule.endDate,
                                post.schedule.scheduleType,
                                post.assistanceTime.assistanceStartTime,
                                post.assistanceTime.assistanceEndTime
                        ),
                        matching.matchingStatus,
                        matching.certificationTracking.requestedAt.lt(LocalDateTime.now().minusDays(1))
                ))
                .from(matching)
                .leftJoin(matching.post, post)
                .where(
                        memberRoleExpression(memberRole, author),
                        completedMatchingStatusExpression(memberRole, isCompleted)
                )
                .offset((long) page * size)
                .limit(size)
                .orderBy(buildOrderSpecifier(sort, post))
                .fetch();

        Long totalElements = queryFactory
                .select(matching.count())
                .from(matching)
                .where(
                        memberRoleExpression(memberRole, author),
                        completedMatchingStatusExpression(memberRole, isCompleted)
                )
                .fetchOne();

        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        return new CompletedVolunteerPostPage(content, totalElements, last);
    }

    private BooleanExpression completedMatchingStatusExpression(MemberRole memberRole, Boolean isCompleted) {

        if (isCompleted == null || !isCompleted) {
            return switch (memberRole) {
                case TAKER, GIVER -> matching.matchingStatus.in(MatchingStatus.DONE, MatchingStatus.VOLUNTEERING_COMPLETED, MatchingStatus.VOLUNTEERING_VERIFIED);
            };
        }

        return switch (memberRole) {
            case TAKER -> matching.matchingStatus.in(MatchingStatus.VOLUNTEERING_COMPLETED, MatchingStatus.VOLUNTEERING_VERIFIED);
            case GIVER -> matching.matchingStatus.eq(MatchingStatus.VOLUNTEERING_VERIFIED);
        };
    }

    private BooleanExpression memberRoleExpression(MemberRole memberRole, Member author) {
        return switch (memberRole) {
            case TAKER -> matching.taker.eq(author);
            case GIVER -> matching.giver.eq(author);
        };
    }

    private BooleanExpression buildCursorExpression(LocalDateTime cursor) {
        return cursor == null ? null : chatMessage.createdAt.lt(cursor);
    }

    private BooleanExpression buildMatchingStatusExpression(List<MatchingStatus> matchingStatus) {
        return matchingStatus == null ? null : matching.matchingStatus.in(matchingStatus);
    }

    private OrderSpecifier<?> buildOrderSpecifier(String sort, QPost post) {
        return switch (sort.toLowerCase()) {
            case "desc" -> post.createdAt.desc();
            case "asc" -> post.createdAt.asc();
            default -> throw PostInvalidSortValueException.EXCEPTION;
        };
    }
}
