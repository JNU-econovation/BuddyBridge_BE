package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.Post;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.id = :postId")
    Optional<Post> findByIdWithAuthor(@Param("postId") Long postId);

    /**
     * {@link @Filter} 어노테이션을 사용하기 위해서는 findById 메소드를 오버라이드 해야한다. <br> 일반 findById 메소드는 EntityMananger.find 메소드를 사용하기 때문에 @Filter 어노테이션 적용이 불가능하다.
     *
     * @param postId
     * @return
     */
    @Override
    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    Optional<Post> findById(@Param("postId") Long postId);

    List<Post> findByIdIn(List<Long> postIds);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Post p SET p.deleted = true WHERE p IN :posts")
    void softDeleteAllIn(@Param("posts") Set<Post> posts);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Post p WHERE p IN :posts")
    void deleteAllIn(@Param("posts") List<Post> posts);
}
