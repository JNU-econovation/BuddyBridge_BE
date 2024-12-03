package econo.buddybridge.report.service;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.comment.service.CommentService;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.report.dto.ReportRequest;
import econo.buddybridge.report.entity.CommentReport;
import econo.buddybridge.report.exception.ReportCommentAlreadyExistsException;
import econo.buddybridge.report.repository.CommentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentService commentService;
    private final MemberService memberService;

    @Transactional
    public void reportComment(Long commentId, ReportRequest reportRequest, Long memberId) {
        Comment comment = commentService.findCommentByIdWithAuthorOrThrow(commentId);
        Member member = memberService.findMemberByIdOrThrow(memberId);

        if (commentReportRepository.existsByReportedCommentAndReporter(comment, member)) {
            throw ReportCommentAlreadyExistsException.EXCEPTION;
        }

        commentReportRepository.save(CommentReport.of(
                comment,
                member,
                reportRequest.reportType(),
                reportRequest.reportReason()
        ));
    }
}
