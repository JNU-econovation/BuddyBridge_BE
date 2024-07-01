package econo.buddybridge.utils.session;

import jakarta.servlet.http.HttpServletRequest;

public class SessionUtils {

    public static Long getMemberId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("memberId").toString());
    }
}
