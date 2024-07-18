package econo.buddybridge.notification.repository;

import econo.buddybridge.notification.dto.NotificationCustomPage;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepositoryCustom {
    NotificationCustomPage findByMemberId(Long memberId, Integer size, Long cursor);
}
