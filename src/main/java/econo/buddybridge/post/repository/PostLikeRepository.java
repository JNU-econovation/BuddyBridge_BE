package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {

    List<PostLike> findByPostIn(List<Post> posts);
}
