package econo.buddybridge.member.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.member.dto.MemberCustomPage;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
@Tag(name = "관리자 회원 API", description = "관리자 회원 관리 API")
public class AdminMemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<CustomBody<MemberCustomPage>> findMembers(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(defaultValue = "desc", required = false) String sort,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        MemberCustomPage members = memberService.getMembers(page, size, sort);
        return ApiResponseGenerator.success(members, HttpStatus.OK);
    }
}
