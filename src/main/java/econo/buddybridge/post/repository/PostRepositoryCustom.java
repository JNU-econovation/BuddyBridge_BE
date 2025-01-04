package econo.buddybridge.post.repository;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostType;

import java.util.List;

public interface PostRepositoryCustom {

    PostDetailDto findByMemberIdAndPostId(Long memberId, Long postId);

    PostCustomPage findPosts(Long memberId, Integer page, Integer size, String sort, PostType postType,
                             PostStatus postStatus, List<DisabilityType> disabilityType, List<AssistanceType> assistanceType);

    PostCustomPage findPostsMyPage(Long memberId, Integer page, Integer size, String sort, PostType postType);

    PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType);
}
