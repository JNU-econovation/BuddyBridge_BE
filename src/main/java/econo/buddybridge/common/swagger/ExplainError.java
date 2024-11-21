package econo.buddybridge.common.swagger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * 예외 상황에 대한 설명을 제공하는 어노테이션 <br>
 * ExceptionDoc 클래스의 필드에 적용하여 각 예외 상황에 대한 상세 설명을 Swagger 문서에 표시한다.
 *
 * <p>사용 예시:
 * <pre>
 * {@code
 * @ExceptionDoc
 * @NoArgsConstructor(access = AccessLevel.PRIVATE)
 * public class GetCommentExceptionDocs implements SwaggerExceptionDoc {
 *
 *     @ExplainError("게시글이 존재하지 않을 때 발생하는 예외입니다.")
 *     public static final BusinessException 게시글이_존재하지_않을_때 = PostNotFoundException.EXCEPTION;
 *
 *     // ...
 * }
 * }
 * </pre>
 *
 * @see ExceptionDoc
 * @see SwaggerExceptionDoc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExplainError {
    /**
     * 예외 상황에 대한 설명을 지정한다.
     */
    String value() default "";
}
