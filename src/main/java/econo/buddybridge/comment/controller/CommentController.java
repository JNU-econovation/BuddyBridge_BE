package econo.buddybridge.comment.controller;

import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentReqDto;
import econo.buddybridge.comment.service.CommentService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{post-id}")
    public ApiResponse<CustomBody<CommentCustomPage>> getComments(
            @PathVariable("post-id") Long postId,
            @RequestParam("limit") Integer size,
            @RequestParam(defaultValue = "DESC") String order,
            @RequestParam(value = "cursor", required = false) Long cursor
    ) {
        CommentCustomPage comments = commentService.getComments(postId, size, order, cursor);
        return ApiResponseGenerator.success(comments, HttpStatus.OK);
    }

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
