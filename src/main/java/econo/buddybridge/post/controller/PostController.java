package econo.buddybridge.post.controller;

import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping() // pagination 추가하기
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    } 
    
    @PostMapping()
    public ResponseEntity<Long> createPost(@RequestBody PostReqDto postReqDto) {
        Long postId = postService.createPost(postReqDto);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<Post> getPost(@PathVariable Long post_id){
        Post post = postService.findPost(post_id);
        return ResponseEntity.ok(post);
    }

}
