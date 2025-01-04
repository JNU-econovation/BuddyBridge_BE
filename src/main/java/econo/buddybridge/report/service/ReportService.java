package econo.buddybridge.report.service;

import static econo.buddybridge.report.mapper.ReportMapper.toReportCustomPage;
import static econo.buddybridge.report.mapper.ReportMapper.toReportDetailResponse;

import econo.buddybridge.report.dto.ReportCustomPage;
import econo.buddybridge.report.dto.ReportDetailResponse;
import econo.buddybridge.report.entity.CommentReport;
import econo.buddybridge.report.entity.MatchingReport;
import econo.buddybridge.report.entity.PostReport;
import econo.buddybridge.report.entity.Report;
import econo.buddybridge.report.exception.ReportNotFoundException;
import econo.buddybridge.report.repository.ReportRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public ReportDetailResponse getReport(Long reportId) {
        Report report = findReportByIdOrThrow(reportId);
        return toReportDetailResponse(report);
    }

    @Transactional(readOnly = true)
    public ReportCustomPage getReports(Integer page, Integer size, String sort) {
        List<Report> reports = reportRepository.findReports(page, size, sort);
        Long totalReports = reportRepository.totalReports();

        return toReportCustomPage(reports, totalReports, page, size);
    }

    @Transactional(readOnly = true)
    public ReportCustomPage getPostReports(Integer page, Integer size, String sort) {
        List<PostReport> reports = reportRepository.findPostReports(page, size, sort);
        Long totalReports = reportRepository.totalPostReports();

        return toReportCustomPage(reports, totalReports, page, size);
    }

    @Transactional(readOnly = true)
    public ReportCustomPage getCommentReports(Integer page, Integer size, String sort) {
        List<CommentReport> reports = reportRepository.findCommentReports(page, size, sort);
        Long totalReports = reportRepository.totalCommentReports();

        return toReportCustomPage(reports, totalReports, page, size);
    }

    @Transactional(readOnly = true)
    public ReportCustomPage getMatchingReports(Integer page, Integer size, String sort) {
        List<MatchingReport> reports = reportRepository.findMatchingReports(page, size, sort);
        Long totalReports = reportRepository.totalMatchingReports();

        return toReportCustomPage(reports, totalReports, page, size);
    }

    @Transactional
    public void deleteReport(Long reportId) {
        Report report = findReportByIdOrThrow(reportId);
        reportRepository.delete(report);
    }

    private Report findReportByIdOrThrow(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }
}
