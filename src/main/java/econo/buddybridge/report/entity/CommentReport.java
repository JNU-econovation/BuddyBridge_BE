package econo.buddybridge.report.entity;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.report.exception.ReportSelfCommentException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("COMMENT")
@Table(name = "COMMENT_REPORT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)  // 하나의 댓글은 여러 신고를 받을 수 있음
    @JoinColumn(name = "reported_comment_id")
    private Comment reportedComment;

    @Builder
    private CommentReport(Member reporter, Member reported, ReportType reportType, String reportReason, Comment reportedComment) {
        super(reporter, reported, reportType, reportReason);
        this.reportedComment = reportedComment;
    }

    public static CommentReport of(Comment comment, Member member, String reportType, String reportReason) {
        if (comment.getAuthor().equals(member)) {
            throw ReportSelfCommentException.EXCEPTION;
        }

        return CommentReport.builder()
                .reporter(member)
                .reported(comment.getAuthor())
                .reportType(ReportType.fromValue(reportType))
                .reportReason(reportReason)
                .reportedComment(comment)
                .build();
    }
}
