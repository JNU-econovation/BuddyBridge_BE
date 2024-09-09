package econo.buddybridge.post.repository;

import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.entity.PostType;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepositoryCustom {

    PostLike findByPostIdAndMemberId(Long postId, Long memberId);

    PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType);
}
