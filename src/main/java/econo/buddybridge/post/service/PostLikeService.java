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
        PostLike existingLike = postLikeRepository.findByPostIdAndMemberId(postId, memberId);

        Member member = memberService.findMemberByIdOrThrow(memberId);
        Post post = postService.findPostByIdOrThrow(postId);

        if (existingLike != null) {
            postLikeRepository.delete(existingLike);
            return false; // 좋아요 취소
        } else {
            PostLike newLike = new PostLike(post, member);
            postLikeRepository.save(newLike);
            return true; // 좋아요 생성
        }
    }
}
