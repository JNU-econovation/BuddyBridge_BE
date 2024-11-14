package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.service.AuthService;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.dto.MemberSignUpReqDto;
import econo.buddybridge.member.dto.MemberSignUpResDto;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "인증 API", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원 가입", description = "회원을 추가합니다.")
    @PostMapping("/signup")
    @AllowAnonymous
    public ApiResponse<CustomBody<MemberSignUpResDto>> signUp(
            @Valid @RequestBody MemberSignUpReqDto memberSignUpReqDto
    ) {
        MemberSignUpResDto memberSignUpResDto = authService.signUp(memberSignUpReqDto);
        return ApiResponseGenerator.success(memberSignUpResDto, HttpStatus.OK);
    }
}
