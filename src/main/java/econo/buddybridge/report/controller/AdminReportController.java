package econo.buddybridge.report.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.comment.dto.CommentResDto;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.report.dto.ReportCustomPage;
import econo.buddybridge.report.dto.ReportDetailResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "관리자 API")
public class AdminReportController {

    private final ReportService reportService;
    private final PostReportService postReportService;
    private final CommentReportService commentReportService;
    private final MatchingReportService matchingReportService;

    @Operation(summary = "신고 내역 상세 조회", description = "신고 내역을 상세 조회합니다.")
    @GetMapping("/{report-id}")
    public ApiResponse<CustomBody<ReportDetailResponse>> getReport(
            @PathVariable("report-id") Long reportId,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        ReportDetailResponse report = reportService.getReport(reportId);
        return ApiResponseGenerator.success(report, HttpStatus.OK);
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

    @Operation(summary = "게시글 내용 조회", description = "신고된 게시글 내용을 조회합니다.")
    @GetMapping("/{report-id}/post")
    public ApiResponse<CustomBody<PostDetailDto>> getPost(
            @PathVariable("report-id") Long reportId,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        PostDetailDto report = postReportService.getPost(reportId);
        return ApiResponseGenerator.success(report, HttpStatus.OK);
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

    @Operation(summary = "댓글 내용 조회", description = "신고된 댓글 내용을 조회합니다.")
    @GetMapping("/{report-id}/comment")
    public ApiResponse<CustomBody<CommentResDto>> getComment(
            @PathVariable("report-id") Long reportId,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        CommentResDto report = commentReportService.getReportedComment(reportId);
        return ApiResponseGenerator.success(report, HttpStatus.OK);
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

    @Operation(summary = "매칭 대화 내용 조회", description = "매칭 대화 내용을 조회합니다.")
    @GetMapping("/{report-id}/chat")
    public ApiResponse<CustomBody<ChatMessageCustomPage>> getChatMessages(
            @PathVariable("report-id") Long reportId,
            @RequestParam("limit") Integer size,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        ChatMessageCustomPage chatMessages = matchingReportService.getMatchingRoomMessages(reportId, size, cursor);
        return ApiResponseGenerator.success(chatMessages, HttpStatus.OK);
    }

    @Operation(summary = "신고 내역 삭제", description = "신고 내역을 삭제합니다.")
    @DeleteMapping("/{report-id}")
    public ApiResponse<CustomBody<Void>> deleteReport(
            @PathVariable("report-id") Long reportId,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        reportService.deleteReport(reportId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
