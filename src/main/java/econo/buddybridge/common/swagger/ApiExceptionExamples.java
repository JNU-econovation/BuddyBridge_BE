package econo.buddybridge.common.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API 메서드의 예외 응답 예시를 Swagger 문서에 표시하기 위한 어노테이션
 *
 * <p>Controller의 메서드에 적용하여 해당 API에서 발생할 수 있는
 * 예외 상황과 응답 형식을 문서화합니다.
 *
 * <p>사용 예시:
 * <pre>
 * {@code
 * @RestController
 * public class CommentController {
 *     // ...
 *     @ApiExceptionExamples(DeleteCommentExceptionDocs.class)  // 예외 상황을 정의한 클래스를 지정
 *     public ApiResponse<CustomBody<Void>> deleteComment(
 *             // ...
 *     ) {
 *         // ...
 *     }
 * }
 * }
 * </pre>
 *
 * @see SwaggerExceptionDoc
 * @see ExceptionDoc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExceptionExamples {
    /**
     * 해당 API에서 발생할 수 있는 예외들이 정의된 문서화 클래스를 지정합니다.
     */
    Class<? extends SwaggerExceptionDoc> value();
}
