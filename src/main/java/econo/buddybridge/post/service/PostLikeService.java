package econo.buddybridge.post.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return postLikeRepository.findByPostIdAndMemberId(postId, memberId)
                .map(this::removeLike)
                .orElseGet(() -> addLike(post, member));
    }

    private boolean removeLike(PostLike postLike) {
        postLikeRepository.delete(postLike);
        return false;
    }

    private boolean addLike(Post post, Member member) {
        PostLike newLike = new PostLike(post, member);
        postLikeRepository.save(newLike);
        return true;
    }
}
