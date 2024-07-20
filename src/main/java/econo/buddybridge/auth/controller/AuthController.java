package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.dto.kakao.KakaoLoginParams;
import econo.buddybridge.auth.service.OAuthLoginService;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class AuthController {

    private final OAuthLoginService oAuthLoginService;
    private final MemberService memberService;

    // 테스트용 로그인 엔드포인트
    @GetMapping("/login/{member-id}")
    public ApiResponse<CustomBody<MemberResDto>> testLogin(
            @PathVariable("member-id") Long memberId,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);

        MemberResDto member = memberService.findMemberById(memberId);
        if (session == null) {
            session = request.getSession(true);
            session.setAttribute("memberId", member.memberId());
        }
        return ApiResponseGenerator.success(member, HttpStatus.OK);
    }

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

    @PostMapping("/login")
    public ApiResponse<CustomBody<MemberResDto>> login(@RequestBody KakaoLoginParams params, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        MemberResDto memberDto;
        if (session == null) {
            memberDto = handleNewSession(params, request);
        } else {
            memberDto = handleExistingSession(session);
        }

        return ApiResponseGenerator.success(memberDto, HttpStatus.OK);
    }

    private MemberResDto handleNewSession(KakaoLoginParams params, HttpServletRequest request) {
        MemberResDto memberDto = oAuthLoginService.login(params);
        HttpSession session = request.getSession(true);
        session.setAttribute("memberId", memberDto.memberId());
        return memberDto;
    }

    private MemberResDto handleExistingSession(HttpSession session) {
        Object memberIdObj = session.getAttribute("memberId");
        if (memberIdObj == null || memberIdObj.toString().isEmpty()) {
            session.invalidate();
            throw new IllegalArgumentException("세션에 유효한 memberId가 없습니다.");
        }

        Long memberId;
        try {
            memberId = Long.parseLong(memberIdObj.toString());
        } catch (NumberFormatException e) {
            session.invalidate();
            throw new IllegalArgumentException("세션의 memberId 형식이 잘못되었습니다.", e);
        }

        return memberService.findMemberById(memberId);
    }
}
