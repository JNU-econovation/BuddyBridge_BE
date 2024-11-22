package econo.buddybridge.common.swagger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * 예외 문서화를 위한 클래스임을 나타내고 스프링 빈으로 등록하는 어노테이션
 *
 * <p>예외 예시들을 그룹화하고 관리하기 위한 클래스에 적용합니다. <br>
 * 이 클래스들은 {@link SwaggerExceptionDoc} 인터페이스를 구현해야 합니다.
 * 각 예외 케이스는 {@link ExplainError} 어노테이션이 붙은 필드로 정의됩니다.
 *
 * <p>사용 예시:
 * <pre>
 * {@code
 * @ExceptionDoc    // 이 어노테이션을 붙여서 스프링 빈으로 등록
 * @NoArgsConstructor(access = AccessLevel.PRIVATE)
 * public class GetCommentExceptionDocs implements SwaggerExceptionDoc {
 *
 *     // ...
 * }
 * }
 * </pre>
 *
 * @see SwaggerExceptionDoc
 * @see ExplainError
 * @see Component
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented     // JavaDoc에 이 어노테이션의 정보를 표시하도록 지정
@Component
public @interface ExceptionDoc {
    /**
     * 스프링 빈의 이름을 지정합니다.
     *
     * <p>{@link Component} 어노테이션의 value 속성과 동일한 역할을 합니다.
     * 지정하지 않을 경우 기본 빈 명명 규칙을 따릅니다.
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}
