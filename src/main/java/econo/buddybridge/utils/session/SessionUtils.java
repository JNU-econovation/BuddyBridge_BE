package econo.buddybridge.utils.session;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class SessionUtils {

    public static Long getMemberId(HttpServletRequest request) {
        try {
            return Optional.of(Long.parseLong(request.getSession().getAttribute("memberId").toString())).orElse(null);
        } catch (RuntimeException e) {
            return null;
        }
    }

}
