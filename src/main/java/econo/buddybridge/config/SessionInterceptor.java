package econo.buddybridge.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    @Value("${spring.session.redis.namespace}")
    private String namespace;

    private final StringRedisTemplate redisTemplate;

    @Value("${oauth.kakao.url.redirect-url}")
    private String REDIRECT_URL;

    private static final String SESSION_PREFIX = ":sessions:";
    private static final String MEMBER_ID_ATTRIBUTE = "memberId";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        String sessionId = request.getRequestedSessionId();

        if (sessionId == null) {
            return handleInvalidSession(response, "sessionId is null");
        }

        String sessionKey = namespace + SESSION_PREFIX + sessionId;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(sessionKey))) {
            return handleInvalidSession(response, "Redis key " + sessionKey + " does not exist");
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return handleInvalidSession(response, "session is null");
        }

        log.info("SessionInterceptor: session ID {}", session.getId());

        String memberId = String.valueOf(session.getAttribute(MEMBER_ID_ATTRIBUTE));
        if (memberId == null) {
            return handleInvalidSession(response, "memberId is null");
        }

        log.info("SessionInterceptor: memberId {}", memberId);

        return true;
    }

    private boolean handleInvalidSession(HttpServletResponse response, String logMessage) throws IOException {
        log.info("SessionInterceptor: {}", logMessage);
        response.addHeader("Location", REDIRECT_URL);
        response.sendRedirect(REDIRECT_URL);
        return false;
    }
}