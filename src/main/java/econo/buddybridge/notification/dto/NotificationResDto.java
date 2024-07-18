package econo.buddybridge.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.notification.entity.Notification;
import econo.buddybridge.notification.entity.NotificationType;
import java.time.LocalDateTime;

public record NotificationResDto(
    Long id,
    String content,
    String url,
    Boolean isRead,
    NotificationType type,
    LocalDateTime createdAt
) {

    @QueryProjection
    public NotificationResDto {
    }

    public static NotificationResDto of(Notification notification) {
        return new NotificationResDto(
                notification.getId(),
                notification.getContent(),
                notification.getUrl(),
                notification.getIsRead(),
                notification.getType(),
                notification.getCreatedAt()
        );
    }
}
