package econo.buddybridge.report.event;

import econo.buddybridge.common.event.DomainEvent;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.entity.Report;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportCreatedEvent extends DomainEvent {

    private final Member reportedMember;

    public static ReportCreatedEvent from(Report report) {
        return new ReportCreatedEvent(report.getReported());
    }
}
