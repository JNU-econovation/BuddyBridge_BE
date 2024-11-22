package econo.buddybridge.common.docs.notification;

import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.swagger.ExceptionDoc;
import econo.buddybridge.common.swagger.ExplainError;
import econo.buddybridge.common.swagger.SwaggerExceptionDoc;
import econo.buddybridge.member.exception.MemberNotFoundException;
import lombok.NoArgsConstructor;

@ExceptionDoc
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class BatchUpdateNotificationExceptionDocs implements SwaggerExceptionDoc {

    @ExplainError("알림을 읽으려는 회원이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 회원이_존재하지_않을_때 = MemberNotFoundException.EXCEPTION;
}
