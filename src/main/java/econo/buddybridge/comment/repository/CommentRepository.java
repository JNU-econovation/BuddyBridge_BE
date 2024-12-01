package econo.buddybridge.comment.repository;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    boolean existsByPostAndAuthor(Post post, Member author);

    @Query("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.id = :commentId")
    Optional<Comment> findByIdWithAuthor(@Param("commentId") Long commentId);
}
