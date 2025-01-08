package econo.buddybridge.certification.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.service.VolunteerCertificationService;
import econo.buddybridge.member.entity.Role;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "관리자 API 봉사활동 인증 폼", description = "봉사활동 인증 폼 관련 관리자 API")
public class AdminCertificationController {

    private final VolunteerCertificationService volunteerCertificationService;

    @Operation(summary = "인증 폼 전체 조회", description = "인증 폼 전체를 조회합니다.")
    @GetMapping("/certifications")
    public ApiResponse<CustomBody<VolunteerCertificationCustomPage>> getVolunteerCertifications(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        VolunteerCertificationCustomPage volunteerCertificationCustomPage = volunteerCertificationService.getVolunteerCertifications(page, size, sort);
        return ApiResponseGenerator.success(volunteerCertificationCustomPage, HttpStatus.OK);
    }

    @Operation(summary = "인증 폼 삭제", description = "인증 폼을 삭제합니다.")
    @DeleteMapping("/certifications/{certification-id}")
    public ApiResponse<CustomBody<Void>> deleteVolunteerCertification(
            @PathVariable("certification-id") Long certificationId,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        volunteerCertificationService.deleteVolunteerCertification(certificationId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
