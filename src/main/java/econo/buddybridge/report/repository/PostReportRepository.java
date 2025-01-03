package econo.buddybridge.report.repository;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.report.entity.PostReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    boolean existsByReportedPostAndReporter(Post reportedPost, Member reporter);

    @Query("SELECT pr.reportedPost FROM PostReport pr WHERE pr.reportedPost IN :reportedPosts")
    List<Post> findByReportedPostIn(List<Post> reportedPosts);
}
