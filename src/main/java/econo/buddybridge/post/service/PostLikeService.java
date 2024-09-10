package econo.buddybridge.post.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final MemberService memberService;
    private final PostService postService;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public Boolean managePostLike(Long memberId, Long postId) {

        Member member = memberService.findMemberByIdOrThrow(memberId);
        Post post = postService.findPostByIdOrThrow(postId);
        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndMemberId(postId, memberId);

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            return false; // 좋아요 취소
        } else {
            PostLike newLike = new PostLike(post, member);
            postLikeRepository.save(newLike);
            return true; // 좋아요 생성
        }
    }

    @Transactional(readOnly = true)
    public PostCustomPage getPostsLikes(Long memberId, Integer page, Integer size, String sort, PostType postType) {
        return postLikeRepository.findPostsByLikes(memberId, page - 1, size, sort, postType);
    }
}
