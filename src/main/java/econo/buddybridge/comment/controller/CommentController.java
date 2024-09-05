package econo.buddybridge.comment.controller;

import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentReqDto;
import econo.buddybridge.comment.dto.MyPageCommentCustomPage;
import econo.buddybridge.comment.service.CommentService;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "댓글 API", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "내가 작성한 댓글 조회", description = "내가 작성한 게시글의 댓글을 조회합니다.")
    @GetMapping("/my-page")
    public ApiResponse<CustomBody<MyPageCommentCustomPage>> getCommentsMyPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "DESC", required = false) String sort,
            @RequestParam(value = "post-type", required = false) PostType postType,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        MyPageCommentCustomPage comments = commentService.getMyPageComments(page, size, sort, postType, memberId);
        return ApiResponseGenerator.success(comments, HttpStatus.OK);
    }

    @Operation(summary = "댓글 조회", description = "게시글의 댓글을 조회합니다.")
    @GetMapping("/{post-id}")
    @AllowAnonymous
    public ApiResponse<CustomBody<CommentCustomPage>> getComments(
            @PathVariable("post-id") Long postId,
            @RequestParam("limit") Integer size,
            @RequestParam(defaultValue = "DESC") String order,
            @RequestParam(value = "cursor", required = false) Long cursor
    ) {
        CommentCustomPage comments = commentService.getComments(postId, size, order, cursor);
        return ApiResponseGenerator.success(comments, HttpStatus.OK);
    }

    @Operation(summary = "댓글 생성", description = "게시글에 댓글을 생성합니다.")
    @PostMapping("/{post-id}")
    public ApiResponse<CustomBody<Long>> createComment(
            @PathVariable("post-id") Long postId,
            @RequestBody CommentReqDto commentReqDto,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Long createdCommentId = commentService.createComment(commentReqDto, postId, memberId);
        return ApiResponseGenerator.success(createdCommentId, HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PutMapping("/{comment-id}")
    public ApiResponse<CustomBody<Long>> updateComment(
            @PathVariable("comment-id") Long commentId,
            @RequestBody CommentReqDto commentReqDto,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Long updatedCommentId = commentService.updateComment(commentId, commentReqDto, memberId);
        return ApiResponseGenerator.success(updatedCommentId, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{comment-id}")
    public ApiResponse<CustomBody<Void>> deleteComment(
            @PathVariable("comment-id") Long commentId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        commentService.deleteComment(commentId, memberId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
