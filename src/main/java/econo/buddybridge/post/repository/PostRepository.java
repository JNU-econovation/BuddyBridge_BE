package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.Post;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long postId);

}
