package econo.buddybridge.common.docs.comment;

import econo.buddybridge.comment.exception.CommentInvalidDirectionException;
import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.swagger.ExceptionDoc;
import econo.buddybridge.common.swagger.ExplainError;
import econo.buddybridge.common.swagger.SwaggerExceptionDoc;
import econo.buddybridge.post.exception.PostNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@ExceptionDoc
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCommentExceptionDocs implements SwaggerExceptionDoc {

    @ExplainError("게시글이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 게시글이_존재하지_않을_때 = PostNotFoundException.EXCEPTION;

    @ExplainError("댓글 정렬 기준이 잘못되었을 때 발생하는 예외입니다")
    public static final BusinessException 정렬_기준이_잘못되었을_때 = CommentInvalidDirectionException.EXCEPTION;
}
