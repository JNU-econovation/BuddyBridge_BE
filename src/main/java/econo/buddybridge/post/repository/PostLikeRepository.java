package econo.buddybridge.post.repository;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM PostLike pl WHERE pl.post IN :posts")
    void deleteAllByPostIn(List<Post> posts);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM PostLike pl WHERE pl.member IN :members")
    void deleteAllByMemberIn(List<Member> members);
}
