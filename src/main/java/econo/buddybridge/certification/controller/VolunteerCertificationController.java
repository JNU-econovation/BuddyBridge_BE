package econo.buddybridge.certification.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.certification.dto.VolunteerCertificationRequest;
import econo.buddybridge.certification.dto.VolunteerCertificationUpdateRequest;
import econo.buddybridge.certification.dto.detail.VolunteeringDetailResponse;
import econo.buddybridge.certification.service.VolunteerCertificationService;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.service.ChatMessageService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matchings")
@RequiredArgsConstructor
@Tag(name = "봉사활동 인증 폼 API", description = "봉사활동 인증 폼 관련 API")
public class VolunteerCertificationController {

    private final VolunteerCertificationService volunteerCertificationService;
    private final ChatMessageService chatMessageService;

    @Operation(summary = "봉사 인증 요청 문자 전송", description = "Giver(봉사자)가 봉사를 완료한 후 TAKER(수혜자)에 봉사 인증 요청을 부탁하는 문자를 전송합니다.")
    @PostMapping("/{matching-id}/certification-requests")
    public ApiResponse<CustomBody<ChatMessageResDto>> sendVolunteerCompletionRequest(
            @PathVariable("matching-id") Long matchingId,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        ChatMessageResDto chatMessage = chatMessageService.sendVolunteerCompletionRequest(matchingId, memberId);
        return ApiResponseGenerator.success(chatMessage, HttpStatus.OK);
    }

    @Operation(summary = "봉사활동 인증 폼 작성", description = "봉사활동 인증 폼을 작성합니다.")
    @PostMapping("/{matching-id}/certifications")
    public ApiResponse<CustomBody<Void>> submitVolunteerCertification(
            @PathVariable("matching-id") Long matchingId,
            @Valid @RequestBody VolunteerCertificationRequest volunteerCertificationRequest,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        volunteerCertificationService.submitVolunteerCertification(matchingId, volunteerCertificationRequest, memberId);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }

    @Operation(summary = "봉사활동 인증 폼 수정", description = "봉사활동 인증 폼을 수정합니다.")
    @PutMapping("/{matching-id}/certifications/{certification-id}")
    public ApiResponse<CustomBody<Void>> modifyVolunteerCertification(
            @PathVariable("matching-id") Long matchingId,
            @PathVariable("certification-id") Long certificationId,
            @Valid @RequestBody VolunteerCertificationUpdateRequest volunteerCertificationUpdateRequest,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        volunteerCertificationService.modifyVolunteerCertification(matchingId, certificationId, volunteerCertificationUpdateRequest, memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @Operation(summary = "봉사 인증 폼 조회", description = "봉사 인증 폼을 조회합니다.")
    @GetMapping("/{matching-id}/certifications/{certification-id}")
    public ApiResponse<CustomBody<VolunteeringDetailResponse>> getVolunteerCertification(
            @PathVariable("matching-id") Long matchingId,
            @PathVariable("certification-id") Long certificationId,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        VolunteeringDetailResponse response = volunteerCertificationService.getVolunteerCertification(matchingId, certificationId, memberId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
