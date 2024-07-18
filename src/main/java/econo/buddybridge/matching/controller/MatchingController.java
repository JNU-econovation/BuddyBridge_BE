package econo.buddybridge.matching.controller;

import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.dto.MatchingUpdateDto;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/accept")
    public ApiResponse<ApiResponse.CustomBody<Long>> createMatching(
            @RequestBody MatchingReqDto matchingReqDto,
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        Long createdMatchingId = matchingService.createMatchingById(matchingReqDto,memberId);
        return ApiResponseGenerator.success(createdMatchingId,HttpStatus.OK);
    }

    // matching 완료 -> DONE 변경, 거절 -> FAILED 변경
    @PutMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<Long>> updateMatching(
            @PathVariable("matching-id") Long matchingId,
            @RequestBody MatchingUpdateDto matchingUpdateDto,
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        Long updatedMatchingId = matchingService.updateMatching(matchingId,matchingUpdateDto,memberId);
        return ApiResponseGenerator.success(updatedMatchingId,HttpStatus.OK);
    }

    // matching 완전 삭제 -> 좋은 방법인가
    // 실수로 제거한 경우는 채팅방을 다시 만들어야하는지
    @DeleteMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deleteMatching(
            @PathVariable("matching-id") Long matchingId,
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        matchingService.deleteMatching(matchingId,memberId);
        return ApiResponseGenerator.success(HttpStatus.NO_CONTENT);
    }
}
