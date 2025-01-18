package econo.buddybridge.report.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.post.entity.PostType;
import java.time.LocalDate;

public record ReportListItem(
        Long id,
        Long postId,
        PostType postType,
        String reportContent,
        String reporter,
        String reported,
        String reportType,
        LocalDate reportDate,
        Boolean isBlackListed
) {

    @QueryProjection
    public ReportListItem {
    }
}
