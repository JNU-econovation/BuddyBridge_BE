package econo.buddybridge.utils.session;

import econo.buddybridge.utils.session.exception.SessionNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class SessionUtils {

    public static Long getMemberId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("memberId").toString());
    }

    public static Long getMemberIdOrNull(HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getSession())
                    .map(session -> session.getAttribute("memberId"))
                    .map(SessionUtils::validateMemberId)
                    .orElse(null);
        } catch (SessionNotFoundException e) {
            return null;
        }
    }

    public static Long validateMemberId(Object memberId) {
        if(memberId instanceof Integer) {
            return ((Integer) memberId).longValue();
        } else if(memberId instanceof Long) {
            return (Long) memberId;
        } else {
            throw SessionNotFoundException.EXCEPTION;
        }
    }
}
