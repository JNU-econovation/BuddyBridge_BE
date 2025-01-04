package econo.buddybridge.report.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.report.dto.ReportCustomPage;
import econo.buddybridge.report.dto.ReportRequest;
import econo.buddybridge.report.entity.ReportType;
import econo.buddybridge.report.service.CommentReportService;
import econo.buddybridge.report.service.MatchingReportService;
import econo.buddybridge.report.service.PostReportService;
import econo.buddybridge.report.service.ReportService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "신고 API", description = "신고 관련 API")
public class ReportController {

    private final ReportService reportService;
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

    @Operation(summary = "신고 내역 전체 조회", description = "신고 내역 전체를 조회합니다.")
    @GetMapping
    public ApiResponse<CustomBody<ReportCustomPage>> getReports(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        ReportCustomPage reports = reportService.getReports(page, size, sort);
        return ApiResponseGenerator.success(reports, HttpStatus.OK);
    }

    @Operation(summary = "게시글 신고 내역 조회", description = "게시글 신고 내역을 조회합니다.")
    @GetMapping("/posts")
    public ApiResponse<CustomBody<ReportCustomPage>> getPostReports(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        ReportCustomPage reports = reportService.getPostReports(page, size, sort);
        return ApiResponseGenerator.success(reports, HttpStatus.OK);
    }

    @Operation(summary = "댓글 신고 내역 조회", description = "댓글 신고 내역을 조회합니다.")
    @GetMapping("/comments")
    public ApiResponse<CustomBody<ReportCustomPage>> getCommentReports(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        ReportCustomPage reports = reportService.getCommentReports(page, size, sort);
        return ApiResponseGenerator.success(reports, HttpStatus.OK);
    }

    @Operation(summary = "매칭 신고 내역 조회", description = "매칭 신고 내역을 조회합니다.")
    @GetMapping("/matchings")
    public ApiResponse<CustomBody<ReportCustomPage>> getMatchingReports(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        ReportCustomPage reports = reportService.getMatchingReports(page, size, sort);
        return ApiResponseGenerator.success(reports, HttpStatus.OK);
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

    @Operation(summary = "신고 내역 삭제", description = "신고 내역을 삭제합니다.")
    @DeleteMapping("/{report-id}")
    public ApiResponse<CustomBody<Void>> deleteReport(
            @PathVariable("report-id") Long reportId,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = Role.ADMIN) Long memberId
    ) {
        reportService.deleteReport(reportId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
