package econo.buddybridge.comment.repository;

import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryCustom {
    CommentCustomPage findByPost(Post post, Long cursor, Pageable page);
}
