package econo.buddybridge.report.repository;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.report.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    boolean existsByReportedPostAndReporter(Post reportedPost, Member reporter);
}
