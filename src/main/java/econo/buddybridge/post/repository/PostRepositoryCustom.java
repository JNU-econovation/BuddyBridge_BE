package econo.buddybridge.post.repository;

import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryCustom {

    PostCustomPage findPosts(Integer page, Integer size, String sort, PostType postType, PostStatus postStatus);
}
