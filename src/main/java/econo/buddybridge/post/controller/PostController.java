package econo.buddybridge.post.controller;

import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    // 커스텀 페이지네이션을 사용한 전체 게시글 조회
    @GetMapping()
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> getAllPosts(
            @RequestParam(value="post-type",required = false) PostType postType,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue="desc",required = false) String sort
    ) {
        PostCustomPage posts = postService.getPosts(page,size,sort,postType);
        return ApiResponseGenerator.success(posts, HttpStatus.OK);
    }

    // 게시글 생성
    @PostMapping()
    public ApiResponse<ApiResponse.CustomBody<Long>> createPost(
            @RequestBody PostReqDto postReqDto,
            HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        Long createdPostId = postService.createPost(postReqDto,memberId);
        return ApiResponseGenerator.success(createdPostId, HttpStatus.CREATED);
    }

    // 단일 게시글 조회
    @GetMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<PostResDto>> getPost(@PathVariable("post-id") Long postId){
        PostResDto postResDto = postService.findPost(postId);
        return ApiResponseGenerator.success(postResDto,HttpStatus.OK);
    }

    // 게시글 업데이트
    @PutMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Long>> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestBody PostReqDto postReqDto,
            HttpServletRequest request){
        Long memberId = SessionUtils.getMemberId(request);
        Long updatedPostId = postService.updatePost(postId,postReqDto,memberId);
        return ApiResponseGenerator.success(updatedPostId,HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deletePost(
            @PathVariable("post-id") Long postId,
            HttpServletRequest request){
        Long memberId = SessionUtils.getMemberId(request);
        postService.deletePost(postId,memberId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }

}
