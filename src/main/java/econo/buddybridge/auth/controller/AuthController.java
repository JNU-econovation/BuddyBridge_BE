package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.dto.LoginReqDto;
import econo.buddybridge.auth.jwt.AuthToken;
import econo.buddybridge.auth.resolver.MemberToken;
import econo.buddybridge.auth.service.AuthService;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.dto.MemberSignUpReqDto;
import econo.buddybridge.member.dto.MemberSignUpResDto;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "자체 로그인 (JWT)", description = "이메일과 비밀번호를 이용해 JWT 토큰을 발급합니다.")
    @PostMapping("/login")
    @AllowAnonymous
    public ApiResponse<CustomBody<AuthToken>> login(
            @Valid @RequestBody LoginReqDto params
    ) {
        AuthToken authToken = authService.loginWithToken(params);
        return ApiResponseGenerator.success(authToken, HttpStatus.OK);
    }

    @Operation(summary = "Access Token, Refresh Token 재발급", description = "Refresh Token을 이용해 Access Token과 Refresh Token을 재발급합니다.")
    @PostMapping("/reissue")
    public ApiResponse<CustomBody<AuthToken>> reissue(@Parameter(hidden = true) @MemberToken String token) {
        AuthToken authToken = authService.reissue(token);
        return ApiResponseGenerator.success(authToken, HttpStatus.OK);
    }
}
