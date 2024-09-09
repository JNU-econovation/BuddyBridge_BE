package econo.buddybridge.auth.controller;

import econo.buddybridge.auth.exception.SessionAlreadyExistsException;
import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Profile({"dev", "local"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@Tag(name = "인증 API")
public class AuthTestController {

    private final MemberService memberService;

    // 테스트용 로그인 엔드포인트
    @Operation(summary = "테스트용 로그인", description = "회원 ID로 로그인합니다. 개발 및 로컬 환경에서만 사용 가능합니다.")
    @AllowAnonymous
    @GetMapping("/login/{member-id}")
    public ApiResponse<CustomBody<MemberResDto>> testLogin(
            @PathVariable("member-id") Long memberId,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 정보를 삭제한다
            throw SessionAlreadyExistsException.EXCEPTION;
        }

        session = request.getSession(true); // 새로운 세션을 생성한다

        MemberResDto member = memberService.findMemberById(memberId);
        session.setAttribute("memberId", member.memberId());

        return ApiResponseGenerator.success(member, HttpStatus.OK);
    }
}
