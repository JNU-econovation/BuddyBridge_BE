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

        if (method.isAnnotationPresent(WithDeletedContent.class)) {
            return joinPoint.proceed();
        }

        try {
            sessionFilterManager.enableFilter(DELETED_FILTER, DELETED_PARAM, false);
            return joinPoint.proceed();
        } finally {
            sessionFilterManager.disableFilter(DELETED_FILTER);
        }
    }
}
