package econo.buddybridge.report.repository;

import econo.buddybridge.report.entity.CommentReport;
import econo.buddybridge.report.entity.MatchingReport;
import econo.buddybridge.report.entity.PostReport;
import econo.buddybridge.report.entity.Report;
import java.util.List;

public interface ReportRepositoryCustom {

    List<Report> findReports(Integer page, Integer size, String sort);

    List<PostReport> findPostReports(Integer page, Integer size, String sort);

    List<CommentReport> findCommentReports(Integer page, Integer size, String sort);

    List<MatchingReport> findMatchingReports(Integer page, Integer size, String sort);

    Long totalReports();

    Long totalPostReports();

    Long totalCommentReports();

    Long totalMatchingReports();
}
