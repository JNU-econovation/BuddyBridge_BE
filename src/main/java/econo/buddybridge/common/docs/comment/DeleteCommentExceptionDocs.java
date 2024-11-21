package econo.buddybridge.common.docs.comment;

import econo.buddybridge.comment.exception.CommentDeleteNotAllowedException;
import econo.buddybridge.comment.exception.CommentNotFoundException;
import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.swagger.ExceptionDoc;
import econo.buddybridge.common.swagger.ExplainError;
import econo.buddybridge.common.swagger.SwaggerExceptionDoc;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@ExceptionDoc
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteCommentExceptionDocs implements SwaggerExceptionDoc {

    @ExplainError("삭제하려는 댓글이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 댓글이_존재하지_않을_때 = CommentNotFoundException.EXCEPTION;

    @ExplainError("다른 사용자의 댓글을 삭제하려고 할 때 발생하는 예외입니다")
    public static final BusinessException 다른_사용자의_댓글을_삭제하려고_할_때 = CommentDeleteNotAllowedException.EXCEPTION;
}
