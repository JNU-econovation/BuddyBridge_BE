package econo.buddybridge.common.docs.comment;

import econo.buddybridge.comment.exception.CommentAlreadyWrittenException;
import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.swagger.ExceptionDoc;
import econo.buddybridge.common.swagger.ExplainError;
import econo.buddybridge.common.swagger.SwaggerExceptionDoc;
import econo.buddybridge.member.exception.MemberNotFoundException;
import econo.buddybridge.post.exception.PostNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@ExceptionDoc
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommentExceptionDocs implements SwaggerExceptionDoc {

    @ExplainError("회원이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 회원이_존재하지_않을_때 = MemberNotFoundException.EXCEPTION;

    @ExplainError("게시글이 존재하지 않을 때 발생하는 예외입니다")
    public static final BusinessException 게시글이_존재하지_않을_때 = PostNotFoundException.EXCEPTION;

    @ExplainError("해당 게시글에 이미 댓글을 작성했을 때 발생하는 예외입니다")
    public static final BusinessException 이미_댓글을_작성했을_때 = CommentAlreadyWrittenException.EXCEPTION;
}
