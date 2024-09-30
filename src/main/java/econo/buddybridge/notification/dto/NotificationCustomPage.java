package econo.buddybridge.notification.dto;

import java.util.List;

public record NotificationCustomPage(
        List<NotificationResDto> content,
        Long cursor,
        Boolean nextPage,
        Long totalUnreadCount
) {

}
