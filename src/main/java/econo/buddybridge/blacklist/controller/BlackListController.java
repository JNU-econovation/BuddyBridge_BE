package econo.buddybridge.blacklist.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.blacklist.dto.BlackListRequest;
import econo.buddybridge.blacklist.service.BlackListService;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/blacklist")
@RequiredArgsConstructor
@Tag(name = "관리자 블랙리스트 관리 API", description = "블랙리스트 관리 관련 관리자 API")
public class BlackListController {

    private final BlackListService blackListService;

    @PostMapping
    public ApiResponse<CustomBody<Void>> registerBlackList(
            @Valid @RequestBody BlackListRequest blackListRequest,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        blackListService.registerBlackListMember(blackListRequest);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @DeleteMapping
    public ApiResponse<CustomBody<Void>> deleteBlackList(
            @Valid @RequestBody BlackListRequest blackListRequest,
            @Parameter(hidden = true) @MemberTokenId(allowedRoles = {Role.ADMIN}) Long memberId
    ) {
        blackListService.deleteBlackListMember(blackListRequest);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
