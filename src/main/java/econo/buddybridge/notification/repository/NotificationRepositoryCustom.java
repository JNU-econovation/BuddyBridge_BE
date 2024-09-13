package econo.buddybridge.notification.repository;

import econo.buddybridge.notification.dto.NotificationCustomPage;

public interface NotificationRepositoryCustom {
    NotificationCustomPage findByMemberId(Long memberId, Integer size, Long cursor);
}
