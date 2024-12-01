package econo.buddybridge.report.entity;

import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.member.entity.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("MATCHING")
@Table(name = "MATCHING_REPORT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingReport extends Report {

    @OneToOne(fetch = FetchType.LAZY)   // 매칭은 하나의 신고만 받을 수 있음 (1:1)
    @JoinColumn(name = "reported_matching_id")
    private Matching reportedMatching;

    @Builder
    public MatchingReport(Member reporter, Member reported, ReportType reportType, String reportReason, Matching reportedMatching) {
        super(reporter, reported, reportType, reportReason);
        this.reportedMatching = reportedMatching;
    }

    public static MatchingReport of(Matching matching, Member member, String reportType, String reportReason) {
        // 매칭 신고는 신고자와 신고 대상이 같을 수 없음
        Member reported;
        if (matching.getGiver().equals(member)) {
            reported = matching.getTaker();
        } else {
            reported = matching.getGiver();
        }

        return MatchingReport.builder()
                .reporter(member)
                .reported(reported)
                .reportType(ReportType.fromValue(reportType))
                .reportReason(reportReason)
                .reportedMatching(matching)
                .build();
    }
}
