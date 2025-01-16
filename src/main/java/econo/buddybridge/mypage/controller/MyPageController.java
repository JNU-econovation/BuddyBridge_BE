package econo.buddybridge.mypage.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.comment.dto.MyPageCommentCustomPage;
import econo.buddybridge.comment.service.CommentService;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.post.dto.CompletedVolunteerPostPage;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
@Tag(name = "마이페이지 API", description = "마이페이지 관련 API")
public class MyPageController {

    private final PostService postService;
    private final CommentService commentService;

    @Operation(summary = "내가 작성한 게시글 조회", description = "내가 작성한 게시글 목록을 조회합니다.")
    @GetMapping("/posts")
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> getAllPostsMyPage(
            @RequestParam(value = "post-type", required = false) PostType postType,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        PostCustomPage posts = postService.getPostsMyPage(memberId, page, size, sort, postType);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

    @Operation(summary = "내가 찜한 게시글 목록 조회", description = "찜한 게시글 목록을 조회합니다.")
    @GetMapping("/posts/likes")
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> getPostLikes(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "post-type", required = false) PostType postType,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        PostCustomPage posts = postService.getPostsLikes(memberId, page, size, sort, postType);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

    @Operation(summary = "나의 매칭된(DONE, VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED 상태) 게시글 목록 조회", description = "매칭 완료 이후의 상태(DONE, VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED)를 가진 게시글 목록을 "
            + "조회합니다.")
    @GetMapping("/posts/completed-matchings")
    public ApiResponse<ApiResponse.CustomBody<CompletedVolunteerPostPage>> getCompletedVolunteerPosts(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @RequestParam(defaultValue = "TAKER") MemberRole memberRole,
            @RequestParam(defaultValue = "false", required = false) Boolean isCompleted,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        CompletedVolunteerPostPage posts = postService.getCompletedVolunteerPosts(memberId, page, size, sort, memberRole, isCompleted);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

    @Operation(summary = "내가 작성한 댓글 조회", description = "내가 작성한 게시글의 댓글을 조회합니다.")
    @GetMapping("/comments")
    public ApiResponse<CustomBody<MyPageCommentCustomPage>> getCommentsMyPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "DESC", required = false) String sort,
            @RequestParam(value = "post-type", required = false) PostType postType,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        MyPageCommentCustomPage comments = commentService.getMyPageComments(memberId, page, size, sort, postType);
        return ApiResponseGenerator.success(comments, HttpStatus.OK);
    }
}
