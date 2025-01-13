package econo.buddybridge.comment.repository;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    boolean existsByPostAndAuthor(Post post, Member author);

    @Query("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.id = :commentId")
    Optional<Comment> findByIdWithAuthor(@Param("commentId") Long commentId);

    @Override
    @Query("SELECT c FROM Comment c WHERE c.id = :commentId")
    Optional<Comment> findById(@Param("commentId") Long commentId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c.post IN :posts")
    void deleteAllByPostIn(List<Post> posts);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deleted = true WHERE c IN :comments")
    void softDeleteAllIn(Set<Comment> comments);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c IN :comments")
    void deleteAllIn(List<Comment> comments);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deleted = true WHERE c.post IN :reportedPosts")
    void softDeleteAllByPostIn(Set<Post> reportedPosts);
}
