package econo.buddybridge.report.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.report.dto.ReportRequest;
import econo.buddybridge.report.entity.ReportType;
import econo.buddybridge.report.service.CommentReportService;
import econo.buddybridge.report.service.MatchingReportService;
import econo.buddybridge.report.service.PostReportService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "신고 API", description = "신고 관련 API")
public class ReportController {

    private final PostReportService postReportService;
    private final CommentReportService commentReportService;
    private final MatchingReportService matchingReportService;

    @Operation(summary = "신고 유형 조회", description = "신고 유형을 조회합니다.")
    @GetMapping("/types")
    @AllowAnonymous
    public ApiResponse<CustomBody<List<String>>> getReportTypes() {
        List<String> types = Arrays.stream(ReportType.values())
                .map(ReportType::getValue)
                .toList();
        return ApiResponseGenerator.success(types, HttpStatus.OK);
    }

    @Operation(summary = "게시글 신고", description = "게시글을 신고합니다.")
    @PostMapping("/posts/{post-id}")
    public ApiResponse<CustomBody<Void>> reportPost(
            @PathVariable("post-id") Long postId,
            @Valid @RequestBody ReportRequest reportRequest,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        postReportService.reportPost(postId, reportRequest, memberId);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 신고", description = "댓글을 신고합니다.")
    @PostMapping("/comments/{comment-id}")
    public ApiResponse<CustomBody<Void>> reportComment(
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody ReportRequest reportRequest,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        commentReportService.reportComment(commentId, reportRequest, memberId);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }

    @Operation(summary = "매칭 신고", description = "매칭을 신고합니다.")
    @PostMapping("/matchings/{matching-id}")
    public ApiResponse<CustomBody<Void>> reportMatching(
            @PathVariable("matching-id") Long matchingId,
            @Valid @RequestBody ReportRequest reportRequest,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        matchingReportService.reportMatching(matchingId, reportRequest, memberId);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }
}
