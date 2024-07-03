package econo.buddybridge.member.controller;

import econo.buddybridge.member.dto.MemberReqDto;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ApiResponse<CustomBody<MemberResDto>> findMember(HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);

        return ApiResponseGenerator.success(memberService.findMemberById(memberId), HttpStatus.OK);
    }

    @PutMapping("/info")
    public ApiResponse<CustomBody<Void>> updateMember(HttpServletRequest request, @RequestBody MemberReqDto memberReqDto) {
        Long memberId = SessionUtils.getMemberId(request);
        memberService.updateMemberById(memberId, memberReqDto);

        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
