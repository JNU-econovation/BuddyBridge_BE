package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.kakao.KakaoLoginParams;
import econo.buddybridge.auth.service.OAuthLoginService;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
public class AuthController {

    private final OAuthLoginService oAuthLoginService;
    private final MemberService memberService;

    @Value("${oauth.kakao.url.front-url}")
    private String frontUrl;

    @Operation(summary = "로그아웃", description = "세션을 제거합니다.")
    @GetMapping("/logout")
    public ApiResponse<CustomBody<String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            oAuthLoginService.logout(OAuthProvider.KAKAO);
            return ApiResponseGenerator.success("로그아웃 성공", HttpStatus.OK);
        }
        return ApiResponseGenerator.success("이미 로그아웃 상태입니다.", HttpStatus.OK);
    }

    @Operation(summary = "카카오 소셜 로그인 (코드로 로그인)", description = "Redirect URL이 백엔드 주소로 설정될 때 사용합니다.")
    @GetMapping("/login")
    public ApiResponse<CustomBody<MemberResDto>> login(@RequestParam("code") String code, HttpServletRequest request) {
        KakaoLoginParams params = new KakaoLoginParams(code);

        MemberResDto memberDto = oAuthLoginService.login(params);

        HttpSession session = request.getSession(true);
        session.setAttribute("memberId", memberDto.memberId());

        // 프론트엔드 주소로 redirect
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(frontUrl));

        return ApiResponseGenerator.success(memberDto, httpHeaders, HttpStatus.PERMANENT_REDIRECT);
    }

    @Operation(summary = "카카오 소셜 로그인 (토큰으로 로그인)", description = "Redirect URL이 프론트엔드 주소로 설정될 때 사용합니다.")
    @PostMapping("/login")
    public ApiResponse<CustomBody<MemberResDto>> login(@RequestBody KakaoLoginParams params, HttpServletRequest request) {
        MemberResDto memberDto = oAuthLoginService.login(params);

        HttpSession session = request.getSession(true);
        session.setAttribute("memberId", memberDto.memberId());

        return ApiResponseGenerator.success(memberDto, HttpStatus.OK);
    }
}
