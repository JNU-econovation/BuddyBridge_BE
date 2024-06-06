package econo.buddybridge.post.controller;

import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
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

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    // 전체 조회
    @GetMapping()
    public ApiResponse<ApiResponse.CustomBody<Page<PostResDto>>> getAllPosts(
            @PageableDefault(size=10,sort="createdAt",direction= Sort.Direction.DESC) Pageable pageable){
        Page<PostResDto> posts = postService.getAllPosts(pageable);
        return ApiResponseGenerator.success(posts,HttpStatus.OK);
    } 
    
    // 포스트 생성
    @PostMapping()
    public ApiResponse<ApiResponse.CustomBody<Long>> createPost(@RequestBody PostReqDto postReqDto) {
        Long postId = postService.createPost(postReqDto);
        return ApiResponseGenerator.success(postId, HttpStatus.CREATED);
    }

    // 포스트 상세 정보 조회
    @GetMapping("/{post_id}")
    public ApiResponse<ApiResponse.CustomBody<PostResDto>> getPost(@PathVariable Long post_id){
        PostResDto postResDto = postService.findPost(post_id);
        return ApiResponseGenerator.success(postResDto,HttpStatus.OK);
    }

    // 포스트 업데이트
    @PutMapping("/{post_id}")
    public ApiResponse<ApiResponse.CustomBody<PostResDto>> updatePost(@PathVariable Long post_id,@RequestBody PostReqDto postReqDto){
        PostResDto postResDto = postService.updatePost(post_id,postReqDto);
        return ApiResponseGenerator.success(postResDto,HttpStatus.OK);
    }

    // 포스트 삭제
    @DeleteMapping("/{post_id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deletePost(@PathVariable Long post_id){
        postService.deletePost(post_id);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }

}
