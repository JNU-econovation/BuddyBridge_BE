package econo.buddybridge.post.controller;

import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostLikeService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/likes")
@Tag(name = "게시글 좋아요 API", description = "게시글 좋아요 관련 API")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "찜한 게시글 관리", description = "찜한 게시글 관리 찜을(생성, 삭제)합니다.")
    @PostMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Boolean>> managePostLike(
            @PathVariable(name = "post-id") Long postId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Boolean isLike = postLikeService.managePostLike(memberId, postId);
        return ApiResponseGenerator.success(isLike, HttpStatus.OK);
    }

    @Operation(summary = "찜한 게시글 목록 조회", description = "찜한 게시글 목록을 조회합니다.")
    @GetMapping("/my-page")
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> getPostLikes(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "post-type", required = false) PostType postType,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        PostCustomPage posts = postLikeService.getPostsLikes(memberId, page, size, sort, postType);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

}
