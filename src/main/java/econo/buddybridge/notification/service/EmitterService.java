package econo.buddybridge.notification.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.notification.dto.NotificationResDto;
import econo.buddybridge.notification.entity.Notification;
import econo.buddybridge.notification.entity.NotificationType;
import econo.buddybridge.notification.repository.EmitterRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class EmitterService {

    private final EmitterRepository emitterRepository;
    private final NotificationService notificationService;
    private final Long CONNECT_TIMEOUT = 1000L * 60 * 60;

    public SseEmitter connect(String memberId, String lastEventId) {
        String eventId = generateEventIdByMemberId(memberId);

        SseEmitter emitter = emitterRepository.save(eventId, new SseEmitter(CONNECT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(eventId));

        emitter.onTimeout(() -> {
            emitter.complete();
            emitterRepository.deleteById(eventId);
        });

        emitter.onError((error) -> {
            emitter.completeWithError(error);
            emitterRepository.deleteById(eventId);
        });

        // 503 에러를 방지하기 위한 더미 데이터 전송
        sendNotification(emitter, eventId, "EventStream Created. [memberId=" + memberId + "]");

        // 클라이언트가 놓친 이벤트가 있다면 재전송
        if (!lastEventId.isEmpty()) {
            sendLostData(lastEventId, memberId, emitter);
        }

        return emitter;
    }

    private String generateEventIdByMemberId(String memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, Object payload) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("notification")
                    .data(payload));
        } catch (Exception e) {
            emitterRepository.deleteById(eventId);
            emitter.completeWithError(e);
        }
    }

    private void sendLostData(String lastEventId, String memberId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(memberId);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0) // 보내지 않은 이벤트만 필터링
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> sendNotification(emitter, entry.getKey(), entry.getValue()));
    }

    public void send(Member receiver, String content, String url, NotificationType type) {
        Notification notification = Notification.createNotification(receiver, content, url, type);
        notificationService.saveNotification(notification);

        String receiverId = notification.getReceiver().getId().toString();
        String eventId = generateEventIdByMemberId(receiverId);

        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, NotificationResDto.of(notification));
                }
        );
    }
}
