package econo.buddybridge.report.event;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.entity.Report;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportDeletedEvent {

    private final Member reportedMember;

    public static ReportDeletedEvent from(Report report) {
        return new ReportDeletedEvent(report.getReported());
    }
}
