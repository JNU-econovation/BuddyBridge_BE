package econo.buddybridge.member.controller;

import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.member.dto.EmailReqDto;
import econo.buddybridge.member.dto.NicknameReqDto;
import econo.buddybridge.member.service.DuplicationCheckService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/check")
@Tag(name = "중복 확인 API", description = "회원 가입시 중복 확인 API")
public class DuplicationCheckController {

    private final DuplicationCheckService duplicationCheckService;

    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 확인")
    @PostMapping("/email")
    @AllowAnonymous
    public ApiResponse<CustomBody<String>> checkEmail(
            @Valid @RequestBody EmailReqDto email
    ) {
        duplicationCheckService.checkEmail(email);
        return ApiResponseGenerator.success("사용 가능한 이메일입니다.", HttpStatus.OK);
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인")
    @PostMapping("/nickname")
    @AllowAnonymous
    public ApiResponse<CustomBody<String>> checkNickname(
            @Valid @RequestBody NicknameReqDto nickname
    ) {
        duplicationCheckService.checkNickname(nickname);
        return ApiResponseGenerator.success("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }
}
