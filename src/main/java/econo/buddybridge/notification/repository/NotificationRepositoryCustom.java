package econo.buddybridge.notification.repository;

import econo.buddybridge.notification.dto.NotificationCustomPage;
import econo.buddybridge.notification.entity.NotificationType;

public interface NotificationRepositoryCustom {

    NotificationCustomPage findByMemberId(Long memberId, Integer size, Long cursor, NotificationType type, Boolean isRead);
}
