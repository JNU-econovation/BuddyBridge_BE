package econo.buddybridge.report.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.report.dto.ReportRequest;
import econo.buddybridge.report.entity.PostReport;
import econo.buddybridge.report.event.ReportCreatedEvent;
import econo.buddybridge.report.exception.ReportNotFoundException;
import econo.buddybridge.report.exception.ReportPostAlreadyExistsException;
import econo.buddybridge.report.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void reportPost(Long postId, ReportRequest reportRequest, Long memberId) {
        Post post = postService.findPostByIdWithAuthorOrThrow(postId);
        Member member = memberService.findMemberByIdOrThrow(memberId);

        if (postReportRepository.existsByReportedPostAndReporter(post, member)) {
            throw ReportPostAlreadyExistsException.EXCEPTION;
        }

        PostReport report = PostReport.of(
                post,
                member,
                reportRequest.reportType(),
                reportRequest.reportReason()
        );
        postReportRepository.save(report);
        
        publisher.publishEvent(ReportCreatedEvent.from(report));
    }

    @Transactional(readOnly = true)
    public PostDetailDto getPost(Long reportId) {
        PostReport postReport = findReportByIdOrThrow(reportId);
        return postService.findReportedPost(postReport.getReportedPost().getId());
    }

    private PostReport findReportByIdOrThrow(Long reportId) {
        return postReportRepository.findById(reportId)
                .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }
}
