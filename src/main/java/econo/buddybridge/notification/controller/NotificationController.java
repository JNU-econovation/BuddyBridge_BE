package econo.buddybridge.notification.controller;

import econo.buddybridge.notification.dto.NotificationCustomPage;
import econo.buddybridge.notification.service.NotificationService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ApiResponse<CustomBody<NotificationCustomPage>> getNotifications(
            @RequestParam("limit") Integer size,
            @RequestParam(value = "cursor", required = false) Long cursor,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        NotificationCustomPage notifications = notificationService.getNotifications(memberId, size, cursor);
        return ApiResponseGenerator.success(notifications, HttpStatus.OK);
    }

    @PatchMapping("/{notification-id}/read")
    public ApiResponse<CustomBody<Void>> markAsRead(
            @PathVariable("notification-id") Long notificationId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        notificationService.markAsRead(notificationId, memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
