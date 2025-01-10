package econo.buddybridge.report.repository;

import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.report.entity.MatchingReport;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchingReportRepository extends JpaRepository<MatchingReport, Long> {

    boolean existsByReportedMatchingAndReporter(Matching matching, Member member);

    boolean existsByReportedMatching(Matching reportedMatching);

    @Query("SELECT m.reportedMatching.post FROM MatchingReport m WHERE m.reportedMatching.post IN :posts")
    Set<Post> findPostByReportedMatchingPostIn(List<Post> posts);
}
