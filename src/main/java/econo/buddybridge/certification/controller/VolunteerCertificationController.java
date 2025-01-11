package econo.buddybridge.certification.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.service.VolunteerCertificationService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matchings")
@RequiredArgsConstructor
@Tag(name = "봉사활동 인증 폼 API", description = "봉사활동 인증 폼 관련 API")
public class VolunteerCertificationController {

    private final VolunteerCertificationService volunteerCertificationService;

    @Operation(summary = "봉사활동 인증 폼 작성", description = "봉사활동 인증 폼을 작성합니다.")
    @PostMapping("/{matching-id}")
    public ApiResponse<CustomBody<Void>> submitVolunteerCertification(
            @PathVariable("matching-id") Long matchingId,
            @Valid @RequestBody VolunteerCertificationRequest volunteerCertificationRequest,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        volunteerCertificationService.submitVolunteerCertification(matchingId, volunteerCertificationRequest, memberId);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }
}
