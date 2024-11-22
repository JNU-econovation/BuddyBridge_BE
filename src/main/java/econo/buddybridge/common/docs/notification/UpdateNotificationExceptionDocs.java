package econo.buddybridge.common.docs.notification;

import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.swagger.ExceptionDoc;
import econo.buddybridge.common.swagger.ExplainError;
import econo.buddybridge.common.swagger.SwaggerExceptionDoc;
import econo.buddybridge.member.exception.MemberNotFoundException;
import econo.buddybridge.notification.exception.NotificationAccessDeniedException;
import econo.buddybridge.notification.exception.NotificationNotFoundException;
import lombok.NoArgsConstructor;

@ExceptionDoc
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UpdateNotificationExceptionDocs implements SwaggerExceptionDoc {

    @ExplainError("알림을 읽으려는 회원이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 회원이_존재하지_않을_때 = MemberNotFoundException.EXCEPTION;

    @ExplainError("읽으려는 알림이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 알림이_존재하지_않을_때 = NotificationNotFoundException.EXCEPTION;

    @ExplainError("다른 회원의 알림을 읽으려고 할 때 발생하는 예외입니다")
    public static final BusinessException 다른_회원의_알림을_읽으려고_할_때 = NotificationAccessDeniedException.EXCEPTION;
}
