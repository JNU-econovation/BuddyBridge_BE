package econo.buddybridge.post.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.post.dto.CompletedVolunteerPostPage;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.post.dto.PostEnumResDto;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.dto.PostUpdateReqDto;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "게시글 API", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(summary = "찜한 게시글 목록 조회", description = "찜한 게시글 목록을 조회합니다.")
    @GetMapping("/likes/my-page")
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

    @Operation(summary = "내가 작성한 게시글 조회", description = "내가 작성한 게시글 목록을 조회합니다.")
    @GetMapping("/my-page")
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

    // 커스텀 페이지네이션을 사용한 전체 게시글 조회
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다. 게시글 유형, 상태, 장애 유형, 지원 유형으로 필터링할 수 있습니다.")
    @GetMapping
    @AllowAnonymous
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> getAllPosts(
            @RequestParam(value = "post-type", required = false) PostType postType,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "post-status", required = false) PostStatus postStatus,
            @RequestParam(value = "disability-type", required = false) List<DisabilityType> disabilityType,
            @RequestParam(value = "assistance-type", required = false) List<AssistanceType> assistanceType,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        PostCustomPage posts = postService.getPosts(memberId, page, size, sort, postType, postStatus, disabilityType, assistanceType);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

    @Operation(summary = "매칭된(DONE, VOLUNTEERING_COMPLETED 상태) 게시글 목록 조회", description = "매칭 완료 이후의 상태(DONE, VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED)를 가진 게시글 목록을 조회합니다.")
    @GetMapping("/volunteering/my-page")
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

    // 게시글 생성
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping
    public ApiResponse<ApiResponse.CustomBody<Long>> createPost(
            @Valid @RequestBody PostReqDto postReqDto,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        Long createdPostId = postService.createPost(postReqDto, memberId);
        return ApiResponseGenerator.success(createdPostId, HttpStatus.CREATED);
    }

    // 게시글 생성을 위한 열거형 정보들 제공
    @Operation(summary = "게시글 생성을 위한 열거형 정보들 제공", description = "게시글 생성을 위한 열거형 정보들을 제공합니다.")
    @GetMapping("/enums")
    public ApiResponse<ApiResponse.CustomBody<PostEnumResDto>> getPostEnums() {
        PostEnumResDto postEnums = postService.getPostEnums();
        return ApiResponseGenerator.success(postEnums, HttpStatus.OK);
    }

    // 단일 게시글 조회
    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/{post-id}")
    @AllowAnonymous
    public ApiResponse<ApiResponse.CustomBody<PostDetailDto>> getPost(
            @PathVariable("post-id") Long postId,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        PostDetailDto postDetailDto = postService.findPost(memberId, postId);
        return ApiResponseGenerator.success(postDetailDto, HttpStatus.OK);
    }

    // 게시글 업데이트
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PatchMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Long>> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestBody PostUpdateReqDto postUpdateReqDto,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        Long updatedPostId = postService.updatePost(postId, postUpdateReqDto, memberId);
        return ApiResponseGenerator.success(updatedPostId, HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deletePost(
            @PathVariable("post-id") Long postId,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        postService.deletePost(postId, memberId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
