package econo.buddybridge.report.repository;

import static econo.buddybridge.report.entity.QCommentReport.commentReport;
import static econo.buddybridge.report.entity.QMatchingReport.matchingReport;
import static econo.buddybridge.report.entity.QPostReport.postReport;
import static econo.buddybridge.report.entity.QReport.report;

import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.entity.CommentReport;
import econo.buddybridge.report.entity.MatchingReport;
import econo.buddybridge.report.entity.PostReport;
import econo.buddybridge.report.entity.Report;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Report> findReports(Integer page, Integer size, String sort) {
        return queryFactory
                .selectFrom(report)
                .offset((long) page * size)
                .limit(size)
                .orderBy(report.createdAt.desc())
                .fetch();
    }

    @Override
    public Long totalReports() {
        return queryFactory
                .select(report.count())
                .from(report)
                .fetchOne();
    }

    @Override
    public List<PostReport> findPostReports(Integer page, Integer size, String sort) {
        return queryFactory
                .selectFrom(postReport)
                .offset((long) page * size)
                .limit(size)
                .orderBy(postReport.createdAt.desc())
                .fetch();
    }

    @Override
    public Long totalPostReports() {
        return queryFactory
                .select(postReport.count())
                .from(postReport)
                .fetchOne();
    }

    @Override
    public List<CommentReport> findCommentReports(Integer page, Integer size, String sort) {
        return queryFactory
                .selectFrom(commentReport)
                .offset((long) page * size)
                .limit(size)
                .orderBy(commentReport.createdAt.desc())
                .fetch();
    }

    @Override
    public Long totalCommentReports() {
        return queryFactory
                .select(commentReport.count())
                .from(commentReport)
                .fetchOne();
    }

    @Override
    public List<MatchingReport> findMatchingReports(Integer page, Integer size, String sort) {
        return queryFactory
                .selectFrom(matchingReport)
                .offset((long) page * size)
                .limit(size)
                .orderBy(matchingReport.createdAt.desc())
                .fetch();
    }

    @Override
    public Long totalMatchingReports() {
        return queryFactory
                .select(matchingReport.count())
                .from(matchingReport)
                .fetchOne();
    }

    @Override
    public Long totalReportsByReported(Member reported) {
        return queryFactory
                .select(report.count())
                .from(report)
                .where(report.reported.eq(reported))
                .fetchOne();
    }
}
