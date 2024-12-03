package econo.buddybridge.report.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.service.PostService;
import econo.buddybridge.report.dto.ReportRequest;
import econo.buddybridge.report.entity.PostReport;
import econo.buddybridge.report.exception.ReportPostAlreadyExistsException;
import econo.buddybridge.report.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostService postService;
    private final MemberService memberService;

    @Transactional
    public void reportPost(Long postId, ReportRequest reportRequest, Long memberId) {
        Post post = postService.findPostByIdWithAuthorOrThrow(postId);
        Member member = memberService.findMemberByIdOrThrow(memberId);

        if (postReportRepository.existsByReportedPostAndReporter(post, member)) {
            throw ReportPostAlreadyExistsException.EXCEPTION;
        }

        postReportRepository.save(PostReport.of(
                post,
                member,
                reportRequest.reportType(),
                reportRequest.reportReason()
        ));
    }
}
