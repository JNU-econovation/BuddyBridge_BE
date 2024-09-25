package econo.buddybridge.notification.controller;

import econo.buddybridge.notification.dto.NotificationCustomPage;
import econo.buddybridge.notification.service.NotificationService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@Tag(name = "알림 API", description = "알림 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 조회", description = "알림을 조회합니다.")
    @GetMapping
    public ApiResponse<CustomBody<NotificationCustomPage>> getNotifications(
            @RequestParam("limit") Integer size,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "is-read", required = false) Boolean isRead,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        NotificationCustomPage notifications = notificationService.getNotifications(memberId, size, cursor, isRead);
        return ApiResponseGenerator.success(notifications, HttpStatus.OK);
    }

    @Operation(summary = "알림 읽음 처리", description = "알림을 읽음 처리합니다.")
    @PostMapping("/{notification-id}/read")
    public ApiResponse<CustomBody<Void>> markAsRead(
            @PathVariable("notification-id") Long notificationId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        notificationService.markAsRead(notificationId, memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/read-all")
    public ApiResponse<CustomBody<Void>> markAllAsRead(HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        notificationService.markAllAsRead(memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
