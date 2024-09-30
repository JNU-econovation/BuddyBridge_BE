package econo.buddybridge.post.controller;

import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
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
@RequestMapping("/api/posts")
@Tag(name = "게시글 API", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(summary = "내가 작성한 게시글 조회", description = "내가 작성한 게시글 목록을 조회합니다.")
    @GetMapping("/my-page")
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> getAllPostsMyPage(
            @RequestParam(value = "post-type", required = false) PostType postType,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
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
            @RequestParam(value = "assistance-type", required = false) AssistanceType assistanceType,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        PostCustomPage posts = postService.getPosts(memberId, page, size, sort, postType, postStatus, disabilityType, assistanceType);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

    // 게시글 생성
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping
    public ApiResponse<ApiResponse.CustomBody<Long>> createPost(
            @RequestBody PostReqDto postReqDto,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Long createdPostId = postService.createPost(postReqDto, memberId);
        return ApiResponseGenerator.success(createdPostId, HttpStatus.CREATED);
    }

    // 단일 게시글 조회
    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/{post-id}")
    @AllowAnonymous
    public ApiResponse<ApiResponse.CustomBody<PostResDto>> getPost(
            @PathVariable("post-id") Long postId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        PostResDto postResDto = postService.findPost(memberId, postId);
        return ApiResponseGenerator.success(postResDto, HttpStatus.OK);
    }

    // 게시글 업데이트
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PutMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Long>> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestBody PostReqDto postReqDto,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Long updatedPostId = postService.updatePost(postId, postReqDto, memberId);
        return ApiResponseGenerator.success(updatedPostId, HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deletePost(
            @PathVariable("post-id") Long postId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        postService.deletePost(postId, memberId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }

}
