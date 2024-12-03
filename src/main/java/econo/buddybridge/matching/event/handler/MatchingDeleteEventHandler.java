package econo.buddybridge.matching.event.handler;

import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.event.MatchingDeleteEvent;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.report.repository.MatchingReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MatchingDeleteEventHandler {

    private final MatchingReportRepository matchingReportRepository;
    private final MatchingRepository matchingRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCommentDeleteEvent(MatchingDeleteEvent event) {
        Matching matching = event.getMatching();

        if (matchingReportRepository.existsByReportedMatching(matching)) {
            matching.delete();
        } else {
            matchingRepository.delete(matching);
        }
    }
}
