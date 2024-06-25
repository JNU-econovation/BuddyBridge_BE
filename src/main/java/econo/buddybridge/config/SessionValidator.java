package econo.buddybridge.config;

import econo.buddybridge.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionValidator {

    @Value("${spring.session.redis.namespace}")
    private String namespace;

    private final StringRedisTemplate redisTemplate;
    private final MemberService memberService;

    private static final String SESSION_PREFIX = ":sessions:";
    private static final String MEMBER_ID_ATTRIBUTE = "memberId";

    public boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (!validateSession(session, response)) {
            return false;
        }

        String sessionId = request.getRequestedSessionId();
        if (!validateSessionId(sessionId, response)) {
            return false;
        }

        String sessionKey = namespace + SESSION_PREFIX + sessionId;
        if (!validateSessionKey(sessionKey, response)) {
            return false;
        }

        Long memberId = getMemberIdFromSession(session, response);
        if (memberId == null) {
            return false;
        }

        return validateMember(memberId, session, response);
    }

    private boolean validateSession(HttpSession session, HttpServletResponse response) throws IOException {
        if (session == null) {
            return handleInvalidSession(response, "session is null");
        }
        return true;
    }

    private boolean validateSessionId(String sessionId, HttpServletResponse response) throws IOException {
        if (sessionId == null) {
            return handleInvalidSession(response, "sessionId is null");
        }
        return true;
    }

    private boolean validateSessionKey(String sessionKey, HttpServletResponse response) throws IOException {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(sessionKey))) {
            return handleInvalidSession(response, "Redis key " + sessionKey + " does not exist");
        }
        return true;
    }

    private Long getMemberIdFromSession(HttpSession session, HttpServletResponse response) throws IOException {
        Object member = session.getAttribute(MEMBER_ID_ATTRIBUTE);
        if (member == null) {
            handleInvalidSession(response, "member attribute is null");
            return null;
        }

        try {
            return Long.valueOf(member.toString());
        } catch (NumberFormatException e) {
            handleInvalidSession(response, "member attribute is not a number");
            return null;
        }
    }

    private boolean validateMember(Long memberId, HttpSession session, HttpServletResponse response) throws IOException {
        if (!memberService.existsById(memberId)) {
            session.invalidate();
            return handleInvalidSession(response, "member does not exist in the database");
        }
        return true;
    }

    private boolean handleInvalidSession(HttpServletResponse response, String logMessage) throws IOException {
        log.info("SessionValidator Error: {}", logMessage);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, logMessage);
        return false;
    }
}
