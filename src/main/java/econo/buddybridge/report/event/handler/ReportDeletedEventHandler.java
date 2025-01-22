package econo.buddybridge.report.event.handler;

import econo.buddybridge.blacklist.repository.BlackListRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.event.ReportDeletedEvent;
import econo.buddybridge.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReportDeletedEventHandler {

    private static final int BLACK_LIST_REPORT_THRESHOLD = 3;

    private final ReportRepository reportRepository;
    private final BlackListRepository blackListRepository;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleReportDeletedEvent(ReportDeletedEvent event) {
        Member reportedMember = event.getReportedMember();

        if (!blackListRepository.existsByReportedMember(reportedMember)) {
            return;
        }

        if (reportRepository.totalReportsByReported(reportedMember) < BLACK_LIST_REPORT_THRESHOLD) {
            blackListRepository.deleteByReportedMember(reportedMember);
        }
    }
}
