package econo.buddybridge.post.repository;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;

public interface PostRepositoryCustom {

    PostCustomPage findPosts(Long memberId, Integer page, Integer size, String sort, PostType postType,
                             PostStatus postStatus, DisabilityType disabilityType, AssistanceType assistanceType);

    PostCustomPage findPostsMyPage(Long memberId, Integer page, Integer size, String sort, PostType postType);
}
