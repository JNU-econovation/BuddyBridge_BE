package econo.buddybridge.notification.repository;

import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String eventCachedId, Object event);

    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);

    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);

    void deleteById(String emitterId);
}
