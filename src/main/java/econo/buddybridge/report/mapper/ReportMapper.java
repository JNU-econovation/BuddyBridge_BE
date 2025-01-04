package econo.buddybridge.report.mapper;

import econo.buddybridge.report.dto.CommentReportDetailResponse;
import econo.buddybridge.report.dto.MatchingReportDetailResponse;
import econo.buddybridge.report.dto.ParsedReportInfo;
import econo.buddybridge.report.dto.PostReportDetailResponse;
import econo.buddybridge.report.dto.ReportCustomPage;
import econo.buddybridge.report.dto.ReportDetailResponse;
import econo.buddybridge.report.dto.ReportListItem;
import econo.buddybridge.report.entity.CommentReport;
import econo.buddybridge.report.entity.MatchingReport;
import econo.buddybridge.report.entity.PostReport;
import econo.buddybridge.report.entity.Report;
import econo.buddybridge.report.exception.ReportUnexpectedConvertException;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ReportMapper {

    private static final String REPORT_CONTENT_FORMAT = "%s - %s";

    public static <T extends Report> ReportCustomPage toReportCustomPage(List<T> reports, Long totalElements, Integer page, Integer size) {
        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        List<ReportListItem> content = reports.stream()
                .map(ReportMapper::toReportListItem)
                .toList();

        return new ReportCustomPage(content, totalElements, last);
    }

    private static ReportListItem toReportListItem(Report report) {
        ParsedReportInfo parsedReportInfo = toParsedReportInfo(report);

        return new ReportListItem(
                report.getId(),
                parsedReportInfo.postId(),
                parsedReportInfo.reportedContent(),
                report.getReporter().getName(),
                report.getReported().getName(),
                report.getReportType().getValue(),
                report.getCreatedAt().toLocalDate()
        );
    }

    private static ParsedReportInfo toParsedReportInfo(Report report) {
        Long postId;
        String reportedContent;

        switch (report) {
            case PostReport postReport -> {
                postId = postReport.getReportedPost().getId();
                reportedContent = String.format(REPORT_CONTENT_FORMAT, "게시글", postReport.getReportedPost().getTitle());
            }
            case CommentReport commentReport -> {
                postId = commentReport.getReportedComment().getPost().getId();
                reportedContent = String.format(REPORT_CONTENT_FORMAT, "댓글", commentReport.getReportedComment().getContent());
            }
            case MatchingReport matchingReport -> {
                postId = matchingReport.getReportedMatching().getId();
                reportedContent = String.format(REPORT_CONTENT_FORMAT, "채팅방", matchingReport.getReportedMatching().getId());
            }
            default -> throw ReportUnexpectedConvertException.EXCEPTION;
        }

        return new ParsedReportInfo(postId, reportedContent);
    }

    public static ReportDetailResponse toReportDetailResponse(Report report) {
        return switch (report) {
            case PostReport postReport -> toPostReportDetailResponse(postReport);
            case CommentReport commentReport -> toCommentReportDetailResponse(commentReport);
            case MatchingReport matchingReport -> toMatchingReportDetailResponse(matchingReport);
            default -> throw ReportUnexpectedConvertException.EXCEPTION;
        };
    }

    private static PostReportDetailResponse toPostReportDetailResponse(PostReport postReport) {
        return new PostReportDetailResponse(
                postReport.getId(),
                postReport.getReportedPost().getId(),
                String.format(REPORT_CONTENT_FORMAT, "게시글", postReport.getReportedPost().getTitle()),
                postReport.getReporter().getName(),
                postReport.getReported().getName(),
                postReport.getReportType().getValue(),
                postReport.getCreatedAt().toLocalDate(),
                postReport.getReportReason()
        );
    }

    private static CommentReportDetailResponse toCommentReportDetailResponse(CommentReport commentReport) {
        return new CommentReportDetailResponse(
                commentReport.getId(),
                commentReport.getReportedComment().getPost().getId(),
                commentReport.getReportedComment().getId(),
                String.format(REPORT_CONTENT_FORMAT, "댓글", commentReport.getReportedComment().getContent()),
                commentReport.getReporter().getName(),
                commentReport.getReported().getName(),
                commentReport.getReportType().getValue(),
                commentReport.getCreatedAt().toLocalDate(),
                commentReport.getReportReason()
        );
    }

    private static MatchingReportDetailResponse toMatchingReportDetailResponse(MatchingReport matchingReport) {
        return new MatchingReportDetailResponse(
                matchingReport.getId(),
                matchingReport.getReportedMatching().getPost().getId(),
                matchingReport.getReportedMatching().getId(),
                String.format(REPORT_CONTENT_FORMAT, "채팅방", matchingReport.getReportedMatching().getId()),
                matchingReport.getReporter().getName(),
                matchingReport.getReported().getName(),
                matchingReport.getReportType().getValue(),
                matchingReport.getCreatedAt().toLocalDate(),
                matchingReport.getReportReason()
        );
    }
}
