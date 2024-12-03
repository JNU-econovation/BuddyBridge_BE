package econo.buddybridge.report.repository;

import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.entity.MatchingReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingReportRepository extends JpaRepository<MatchingReport, Long> {

    boolean existsByReportedMatchingAndReporter(Matching matching, Member member);

    boolean existsByReportedMatching(Matching reportedMatching);
}
