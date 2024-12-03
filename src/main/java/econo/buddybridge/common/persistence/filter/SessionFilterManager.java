package econo.buddybridge.common.persistence.filter;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionFilterManager {

    private final EntityManager entityManager;

    /**
     * 필터 활성화
     *
     * @param filter 활성화 할 필터
     * @param paramName 파라미터 이름
     * @param paramValue 파라미터 값
     */
    public void enableFilter(String filter, String paramName, Object paramValue) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter(filter).setParameter(paramName, paramValue);
    }

    /**
     * 필터 비활성화
     *
     * @param filter 비활성화 할 필터
     */
    public void disableFilter(String filter) {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter(filter);
    }

    /**
     * 필터 활성화 여부 확인
     *
     * @param filter 확인할 필터
     * @return 활성화 여부
     */
    public boolean isFilterEnabled(String filter) {
        Session session = entityManager.unwrap(Session.class);
        return session.getEnabledFilter(filter) != null;
    }
}
