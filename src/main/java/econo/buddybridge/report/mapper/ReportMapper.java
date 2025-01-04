package econo.buddybridge.report.mapper;

import econo.buddybridge.report.dto.ReportCustomPage;
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

    public static ReportListItem toReportListItem(Report report) {
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

        return new ReportListItem(
                report.getId(),
                postId,
                reportedContent,
                report.getReporter().getName(),
                report.getReported().getName(),
                report.getReportType().getValue(),
                report.getCreatedAt().toLocalDate()
        );
    }

    public static <T extends Report> ReportCustomPage toReportCustomPage(List<T> reports, Long totalElements, Integer page, Integer size) {
        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        List<ReportListItem> content = reports.stream()
                .map(ReportMapper::toReportListItem)
                .toList();

        return new ReportCustomPage(content, totalElements, last);
    }
}
