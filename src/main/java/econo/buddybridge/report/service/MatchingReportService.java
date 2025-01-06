package econo.buddybridge.report.service;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.service.MatchingRoomService;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.report.dto.ReportRequest;
import econo.buddybridge.report.entity.MatchingReport;
import econo.buddybridge.report.exception.ReportMatchingAlreadyExistsException;
import econo.buddybridge.report.exception.ReportNotFoundException;
import econo.buddybridge.report.repository.MatchingReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingReportService {

    private final MatchingReportRepository matchingReportRepository;
    private final MatchingService matchingService;
    private final MatchingRoomService matchingRoomService;
    private final MemberService memberService;

    @Transactional
    public void reportMatching(Long matchingId, ReportRequest reportRequest, Long memberId) {
        Matching matching = matchingService.findMatchingByIdWithMembers(matchingId);
        Member member = memberService.findMemberByIdOrThrow(memberId);

        if (matchingReportRepository.existsByReportedMatchingAndReporter(matching, member)) {
            throw ReportMatchingAlreadyExistsException.EXCEPTION;
        }

        matchingReportRepository.save(MatchingReport.of(
                matching,
                member,
                reportRequest.reportType(),
                reportRequest.reportReason()
        ));
    }

    @Transactional(readOnly = true)
    public ChatMessageCustomPage getMatchingRoomMessages(Long reportId, Integer size, Long cursor) {
        MatchingReport matchingReport = findReportByIdOrThrow(reportId);
        Member reporter = matchingReport.getReporter();
        Long matchingId = matchingReport.getReportedMatching().getId();
        return matchingRoomService.getReportedMatchingRoomMessages(reporter.getId(), matchingId, size, cursor);
    }

    private MatchingReport findReportByIdOrThrow(Long reportId) {
        return matchingReportRepository.findById(reportId)
                .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }
}
