package econo.buddybridge.post.controller;

import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.api.PostCustomPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    // 기존 전체 조회
//    @GetMapping()
//    public ApiResponse<ApiResponse.CustomBody<Page<PostResDto>>> getAllPosts(
//            @PageableDefault(size=8,sort="createdAt",direction= Sort.Direction.DESC) Pageable pageable) {
//        Page<PostResDto> posts = postService.getAllPosts(pageable);
//        return ApiResponseGenerator.success(posts, HttpStatus.OK);
//    }

    // 커스텀 페이지네이션을 사용한 조회
    @GetMapping()
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage<PostResDto>>> getAllPostsTest(
            @PageableDefault(size=8,sort="createdAt",direction= Sort.Direction.DESC) Pageable pageable) {
        Page<PostResDto> posts = postService.getAllPosts(pageable);

        // customPage 만들기
        PostCustomPage<PostResDto> postCustomPage = new PostCustomPage<>(posts.getContent(),posts.isLast(),posts.getTotalElements());
        return ApiResponseGenerator.success(postCustomPage, HttpStatus.OK);
    }

    // 포스트 생성
    @PostMapping()
    public ApiResponse<ApiResponse.CustomBody<Long>> createPost(@RequestBody PostReqDto postReqDto) {
        Long postId = postService.createPost(postReqDto);
        return ApiResponseGenerator.success(postId, HttpStatus.CREATED);
    }

    // 포스트 상세 정보 조회
    @GetMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<PostResDto>> getPost(@PathVariable("post-id") Long postId){
        PostResDto postResDto = postService.findPost(postId);
        return ApiResponseGenerator.success(postResDto,HttpStatus.OK);
    }

    // 포스트 업데이트
    @PutMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<PostResDto>> updatePost(@PathVariable("post-id") Long postId,@RequestBody PostReqDto postReqDto){
        PostResDto postResDto = postService.updatePost(postId,postReqDto);
        return ApiResponseGenerator.success(postResDto,HttpStatus.OK);
    }

    // 포스트 삭제
    @DeleteMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deletePost(@PathVariable("post-id") Long postId){
        postService.deletePost(postId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }

}
