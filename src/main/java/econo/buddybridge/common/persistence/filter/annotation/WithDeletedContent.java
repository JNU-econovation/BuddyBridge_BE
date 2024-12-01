package econo.buddybridge.common.persistence.filter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithDeletedContent {
    boolean value() default true;   // 삭제된 컨텐츠 포함 여부: true - 포함, false - 미포함
    String filterName() default "deletedFilter";   // 사용할 필터 이름
}
