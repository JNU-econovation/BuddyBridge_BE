package econo.buddybridge.post.service;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.exception.PostDeleteNotAllowedException;
import econo.buddybridge.post.exception.PostNotFoundException;
import econo.buddybridge.post.exception.PostUpdateNotAllowedException;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    // 존재하는 포스트인지 확인
    @Transactional(readOnly = true)
    public Post findPostByIdOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true) // 단일 게시글 조회
    public PostResDto findPost(Long postId) {
        Post post = findPostByIdOrThrow(postId);
        return new PostResDto(post);
    }

    @Transactional(readOnly = true) // 내가 작성한 게시글 조회
    public PostCustomPage getPostsMyPage(Long memberId, Integer page, Integer size, String sort, PostType postType) {
        return postRepository.findPostsMyPage(memberId, page - 1, size, sort, postType);
    }

    @Transactional(readOnly = true) // 전체 게시글 조회
    public PostCustomPage getPosts(Long memberId, Integer page, Integer size, String sort, PostType postType, PostStatus postStatus,
                                   DisabilityType disabilityType, AssistanceType assistanceType) {
        return postRepository.findPosts(memberId, page - 1, size, sort, postType, postStatus, disabilityType, assistanceType);
    }

    // 검증 과정 필요성 고려
    @Transactional // 게시글 생성
    public Long createPost(PostReqDto postReqDto, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);

        Post post = postReqDto.toEntity(member);
        return postRepository.save(post).getId();
    }

    @Transactional // 게시글 수정
    public Long updatePost(Long postId, PostReqDto postReqDto, Long memberId) {
        Post post = findPostByIdOrThrow(postId);

        if (!post.getAuthor().getId().equals(memberId)) {
            throw PostUpdateNotAllowedException.EXCEPTION;
        }

        post.updatePost(postReqDto);

        return post.getId();
    }

    @Transactional // 게시글 삭제
    public void deletePost(Long postId, Long memberId) {
        Post post = findPostByIdOrThrow(postId);
        if (!post.getAuthor().getId().equals(memberId)) {
                throw PostDeleteNotAllowedException.EXCEPTION;
        }
        postRepository.deleteById(postId);
    }
}
