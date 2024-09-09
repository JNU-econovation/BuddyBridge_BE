package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.PostLike;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepositoryCustom {

    PostLike findByPostIdAndMemberId(Long postId, Long memberId);
}
