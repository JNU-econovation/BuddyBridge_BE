package econo.buddybridge.comment.repository;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    boolean existsByPostAndAuthor(Post post, Member author);
}
