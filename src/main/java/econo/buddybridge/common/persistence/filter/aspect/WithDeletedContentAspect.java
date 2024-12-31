package econo.buddybridge.common.persistence.filter.aspect;

import econo.buddybridge.common.persistence.filter.SessionFilterManager;
import econo.buddybridge.common.persistence.filter.annotation.WithDeletedContent;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class WithDeletedContentAspect {

    private static final String DELETED_FILTER = "deletedFilter";
    private static final String DELETED_PARAM = "isDeleted";

    private final SessionFilterManager sessionFilterManager;

    @Around("execution(* econo.buddybridge.*.service.*Service.*(..))")
    public Object handleFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // @WithDeletedContent 어노테이션이 붙어있는 경우 필터를 적용하지 않음 (삭제된 데이터도 조회)
        if (method.isAnnotationPresent(WithDeletedContent.class)) {
            return joinPoint.proceed();
        }

        // 이미 활성화된 필터가 있는 경우
        if (sessionFilterManager.isFilterEnabled(DELETED_FILTER)) {
            return joinPoint.proceed();
        }

        // 필터를 활성화하고 비활성화하는 부분을 try-finally로 감싸서 예외 발생 시에도 필터를 비활성화할 수 있도록 함
        try {
            sessionFilterManager.enableFilter(DELETED_FILTER, DELETED_PARAM, false);
            return joinPoint.proceed();
        } finally {
            sessionFilterManager.disableFilter(DELETED_FILTER);
        }
    }
}
