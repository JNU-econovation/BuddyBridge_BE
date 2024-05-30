package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    // Optional<Post> findById(Long id);
}
