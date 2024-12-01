package econo.buddybridge.report.entity;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.report.exception.ReportSelfPostException;
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
@DiscriminatorValue("POST")
@Table(name = "POST_REPORT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)  // 하나의 게시글은 여러 신고를 받을 수 있음
    @JoinColumn(name = "reported_post_id")
    private Post reportedPost;

    @Builder
    private PostReport(Member reporter, Member reported, ReportType reportType, String reportReason, Post reportedPost) {
        super(reporter, reported, reportType, reportReason);
        this.reportedPost = reportedPost;
    }

    public static PostReport of(Post post, Member member, String reportType, String reportReason) {
        if (post.getAuthor().equals(member)) {
            throw ReportSelfPostException.EXCEPTION;
        }

        return PostReport.builder()
                .reporter(member)
                .reported(post.getAuthor())
                .reportType(ReportType.fromValue(reportType))
                .reportReason(reportReason)
                .reportedPost(post)
                .build();
    }
}
