package econo.buddybridge.matching.controller;

import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.dto.MatchingUpdateDto;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matching")
@Tag(name = "매칭 API", description = "매칭 관련 API")
public class MatchingController {

    private final MatchingService matchingService;

    @Operation(summary = "매칭 생성(수락)", description = "매칭을 생성(수락)합니다.")
    @PostMapping("/accept")
    public ApiResponse<ApiResponse.CustomBody<Long>> createMatching(
            @RequestBody MatchingReqDto matchingReqDto,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Long createdMatchingId = matchingService.createMatchingById(matchingReqDto, memberId);
        return ApiResponseGenerator.success(createdMatchingId, HttpStatus.OK);
    }

    // matching 완료 -> DONE 변경, 거절 -> FAILED 변경
    @Operation(summary = "매칭 상태 변경", description = "매칭 상태를 변경합니다. 매칭 상태(DONE, FAILED, PENDING)에 따라 게시글 상태도 변경됩니다.")
    @PutMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<Long>> updateMatching(
            @PathVariable("matching-id") Long matchingId,
            @RequestBody MatchingUpdateDto matchingUpdateDto,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        Long updatedMatchingId = matchingService.updateMatching(matchingId, matchingUpdateDto, memberId);
        return ApiResponseGenerator.success(updatedMatchingId, HttpStatus.OK);
    }

    // matching 완전 삭제 -> 좋은 방법인가
    // 실수로 제거한 경우는 채팅방을 다시 만들어야하는지
    @Operation(summary = "매칭 삭제", description = "매칭을 삭제합니다.")
    @DeleteMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deleteMatching(
            @PathVariable("matching-id") Long matchingId,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        matchingService.deleteMatching(matchingId, memberId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
