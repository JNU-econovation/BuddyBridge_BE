package econo.buddybridge.member.controller;

import econo.buddybridge.member.dto.MemberReqDto;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "회원 API", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @GetMapping("/info")
    public ApiResponse<CustomBody<MemberResDto>> findMember(HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);

        return ApiResponseGenerator.success(memberService.findMemberById(memberId), HttpStatus.OK);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @PutMapping("/info")
    public ApiResponse<CustomBody<Void>> updateMember(HttpServletRequest request, @RequestBody MemberReqDto memberReqDto) {
        Long memberId = SessionUtils.getMemberId(request);
        memberService.updateMemberById(memberId, memberReqDto);

        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
