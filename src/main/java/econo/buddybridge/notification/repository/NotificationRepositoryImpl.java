package econo.buddybridge.notification.repository;

import static econo.buddybridge.notification.entity.QNotification.notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.notification.dto.NotificationCustomPage;
import econo.buddybridge.notification.dto.NotificationResDto;
import econo.buddybridge.notification.dto.QNotificationResDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public NotificationCustomPage findByMemberId(Long memberId, Integer size, Long cursor, Boolean isRead) {
        List<NotificationResDto> content = queryFactory
                .select(
                        new QNotificationResDto(
                                notification.id,
                                notification.content,
                                notification.url,
                                notification.isRead,
                                notification.type,
                                notification.createdAt
                        )
                )
                .from(notification)
                .where(notification.receiver.id.eq(memberId), notification.createdAt.gt(LocalDateTime.now().minusDays(3)), // 3일 이내 알림만 조회
                        buildCursorPredicate(cursor), buildIsReadPredicate(isRead))
                .orderBy(notification.id.desc())
                .limit(size + 1)
                .fetch();

        Boolean nextPage = false;
        if (content.size() > size) {
            content.removeLast();
            nextPage = true;
        }

        Long nextCursor = nextPage ? content.getLast().id() : -1L;

        Long totalUnreadCount = queryFactory
                .select(notification.count())
                .from(notification)
                .where(notification.receiver.id.eq(memberId),
                        notification.createdAt.gt(LocalDateTime.now().minusDays(3)),
                        notification.isRead.eq(false))
                .fetchOne();

        return new NotificationCustomPage(content, nextCursor, nextPage, totalUnreadCount);
    }

    private BooleanExpression buildCursorPredicate(Long cursor) {
        if (cursor == null || cursor == -1L) {
            return null;
        }
        return notification.id.lt(cursor);
    }

    private BooleanExpression buildIsReadPredicate(Boolean isRead) {
        if (isRead == null) {
            return null;
        }
        return notification.isRead.eq(isRead);
    }
}
