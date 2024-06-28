package econo.buddybridge.post.repository;

import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByPostType(Pageable pageable, PostType postType);
}
