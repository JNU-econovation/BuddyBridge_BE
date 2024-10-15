package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.PostLike;

import java.util.Optional;

public interface PostLikeRepositoryCustom {

    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);
}
