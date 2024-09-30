package econo.buddybridge.post.repository;

import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.entity.PostType;
import java.util.Optional;

public interface PostLikeRepositoryCustom {

    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);

    PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType);
}
