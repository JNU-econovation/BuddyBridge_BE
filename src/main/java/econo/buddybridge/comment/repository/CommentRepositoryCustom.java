package econo.buddybridge.comment.repository;

import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.MyPageCommentCustomPage;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryCustom {
    CommentCustomPage findByPost(Post post, Long cursor, Pageable page);

    MyPageCommentCustomPage findByMember(Integer page, Integer size, String sort, PostType postType, Long id);
}
