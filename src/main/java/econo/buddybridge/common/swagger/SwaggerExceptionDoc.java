package econo.buddybridge.common.swagger;

/**
 * Swagger 문서에 표시될 예외 예시들을 정의하는 인터페이스
 *
 * <p>이 인터페이스를 구현하는 클래스는 {@link ExceptionDoc} 어노테이션과 함께 사용되어야 하며,
 * {@link ExplainError} 어노테이션이 붙은 필드들로 각각의 예외 케이스를 정의해야 합니다.
 *
 * <p>사용 예시:
 * <pre>
 * {@code
 * @ExceptionDoc
 * @NoArgsConstructor(access = AccessLevel.PRIVATE)
 * public class CreateCommentExceptionDocs implements SwaggerExceptionDoc {
 *
 *     @ExplainError("회원이 존재하지 않을 때")
 *     public static final BusinessException 회원이_존재하지_않을_때 = MemberNotFoundException.EXCEPTION;
 *
 *     // 다른 예외 케이스들...
 * }
 * }
 * </pre>
 *
 * <p>이 인터페이스를 구현한 클래스는 다음 규칙을 따라야 합니다:
 * <ul>
 *   <li>모든 예외 필드는 {@code static final}로 선언되어야 합니다</li>
 *   <li>각 예외 필드는 {@link ExplainError} 어노테이션으로 설명되어야 합니다</li>
 *   <li>예외 필드의 이름은 해당 예외 상황을 명확히 설명해야 합니다</li>
 * </ul>
 *
 * @see ExceptionDoc
 * @see ExplainError
 * @see ApiExceptionExamples
 */
public interface SwaggerExceptionDoc {

}
