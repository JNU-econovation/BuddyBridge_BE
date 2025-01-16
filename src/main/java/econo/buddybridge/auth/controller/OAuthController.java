package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.kakao.KakaoLoginParams;
import econo.buddybridge.auth.exception.AlreadyLogoutException;
import econo.buddybridge.auth.jwt.AuthToken;
import econo.buddybridge.auth.resolver.MemberToken;
import econo.buddybridge.auth.service.OAuthLoginService;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@Tag(name = "인증 API", description = "인증 관련 API")
public class OAuthController {

    private final OAuthLoginService oAuthLoginService;

    @Value("${oauth.kakao.url.front-url}")
    private String frontUrl;

    @Operation(summary = "로그아웃", description = "세션을 제거합니다.")
    @AllowAnonymous
    @PostMapping("/logout")
    public ApiResponse<CustomBody<String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            oAuthLoginService.logout(OAuthProvider.KAKAO);
            return ApiResponseGenerator.success("로그아웃 성공", HttpStatus.OK);
        }
        throw AlreadyLogoutException.EXCEPTION;
    }

    @Operation(summary = "카카오 소셜 로그인 (코드로 로그인)", description = "Redirect URL이 백엔드 주소로 설정될 때 사용합니다.")
    @AllowAnonymous
    @GetMapping("/login")
    public void login(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        KakaoLoginParams params = new KakaoLoginParams(code);

        AuthToken authToken = oAuthLoginService.loginWithToken(params);

        String redirectUrl = String.format("%s/?accessToken=%s&refreshToken=%s",
                frontUrl,
                authToken.accessToken(),
                authToken.refreshToken()
        );

        response.sendRedirect(redirectUrl);
    }

    @Operation(summary = "카카오 소셜 로그인 (토큰으로 로그인)", description = "Redirect URL이 프론트엔드 주소로 설정될 때 사용합니다.")
    @AllowAnonymous
    @PostMapping("/login")
    public ApiResponse<CustomBody<MemberResDto>> login(@RequestBody KakaoLoginParams params, HttpServletRequest request) {
        MemberResDto memberDto = oAuthLoginService.login(params);

        HttpSession session = request.getSession(true);
        session.setAttribute("memberId", memberDto.memberId());

        return ApiResponseGenerator.success(memberDto, HttpStatus.OK);
    }

    // 소셜로그인 with JWT
    @Operation(summary = "카카오 소셜 로그인 (JWT)", description = "JWT를 이용하여 로그인합니다.")
    @AllowAnonymous
    @PostMapping("/login/jwt")
    public ApiResponse<CustomBody<AuthToken>> loginWithToken(@RequestBody KakaoLoginParams params) {
        AuthToken authToken = oAuthLoginService.loginWithToken(params);
        return ApiResponseGenerator.success(authToken, HttpStatus.OK);
    }

    // refresh token 재발급
    @Operation(summary = "Access Token, Refresh Token 재발급", description = "Refresh Token을 이용하여 두 토큰 모두 재발급합니다.")
    @PostMapping("/reissue")
    public ApiResponse<CustomBody<AuthToken>> reissue(@Parameter(hidden = true) @MemberToken String token) {
        AuthToken authToken = oAuthLoginService.reissue(token);
        return ApiResponseGenerator.success(authToken, HttpStatus.OK);
    }
}
