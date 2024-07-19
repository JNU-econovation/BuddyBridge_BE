package econo.buddybridge.utils.session;

import jakarta.servlet.http.HttpServletRequest;

public class SessionUtils {

    public static Long getMemberId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("memberId").toString());
    }

    public static Long validateMemberId(Object memberId){
        if(memberId instanceof Integer){
            return ((Integer) memberId).longValue();
        } else if(memberId instanceof Long){
            return (Long) memberId;
        } else{
            throw new IllegalArgumentException("적절하지 않은 memberId 입니다.");
        }
    }
}
