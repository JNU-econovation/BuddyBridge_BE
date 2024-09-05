package econo.buddybridge.notification.controller;

import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.notification.service.EmitterService;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
@Tag(name = "알림 API")
public class EmitterController {

    private final EmitterService emitterService;

    @Operation(summary = "알림 연결", description = "SSE로 알림을 연결합니다.")
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                    String lastEventId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Nginx에서 버퍼링을 사용하지 않도록 설정: X-Accel-Buffering=no
        response.addHeader("X-Accel-Buffering", "no");
        Long memberId = SessionUtils.getMemberId(request);
        return emitterService.connect(memberId.toString(), lastEventId);
    }
}
