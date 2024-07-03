package econo.buddybridge.member.controller;

import econo.buddybridge.member.dto.MemberDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ApiResponse<CustomBody<MemberDto>> findMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object memberIdObj = session.getAttribute("memberId");

        Long memberId = Long.valueOf(memberIdObj.toString());
        System.out.println("memberId = " + memberId);
        return ApiResponseGenerator.success(memberService.findMember(memberId), HttpStatus.OK);
    }
}
