package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long postId);

    Page<Post> findByPostType(Pageable pageable, PostType postType);
}
