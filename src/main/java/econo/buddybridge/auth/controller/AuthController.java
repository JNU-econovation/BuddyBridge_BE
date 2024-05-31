package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.kakao.KakaoLoginParams;
import econo.buddybridge.auth.service.OAuthLoginService;
import econo.buddybridge.member.dto.MemberDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;
    private final MemberService memberService;

    @GetMapping("/api/oauth/logout")
    public ApiResponse<CustomBody<String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            oAuthLoginService.logout(OAuthProvider.KAKAO);
            return ApiResponseGenerator.success("로그아웃 성공", HttpStatus.OK);
        }
        return ApiResponseGenerator.success("이미 로그아웃 상태입니다.", HttpStatus.OK);
    }

    @PostMapping("/api/oauth/login")
    public ApiResponse<?> login(@RequestBody KakaoLoginParams params, final HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        MemberDto memberDto;
        if (session == null) {
            memberDto = handleNewSession(params, request);
        } else {
            memberDto = handleExistingSession(session);
        }

        return ApiResponseGenerator.success(memberDto, HttpStatus.OK);
    }

    private MemberDto handleNewSession(KakaoLoginParams params, HttpServletRequest request) {
        MemberDto memberDto = oAuthLoginService.login(params);
        HttpSession session = request.getSession(true);
        session.setAttribute("memberId", memberDto.memberId());
        return memberDto;
    }

    private MemberDto handleExistingSession(HttpSession session) {
        String memberIdStr = String.valueOf(session.getAttribute("memberId"));
        if (memberIdStr == null || memberIdStr.isEmpty()) {
            throw new IllegalArgumentException("세션에 유효한 memberId가 없습니다.");
        }

        Long memberId;
        try {
            memberId = Long.parseLong(memberIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("세션의 memberId 형식이 잘못되었습니다.", e);
        }

        return memberService.findMember(memberId);
    }
}
