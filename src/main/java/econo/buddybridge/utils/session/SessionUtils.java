package econo.buddybridge.utils.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static Long getMemberId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("memberId").toString());
    }

    public static Long getMemberIdOrNull(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return null;
        }
        Object memberId = session.getAttribute("memberId");
        if (memberId == null) {
            return null;
        }
        return validateMemberId(memberId);
    }

    public static Long validateMemberId(Object memberId) {
        if(memberId instanceof Integer) {
            return ((Integer) memberId).longValue();
        } else if(memberId instanceof Long) {
            return (Long) memberId;
        } else {
            throw new IllegalArgumentException("적절하지 않은 memberId 입니다.");
        }
    }
}
