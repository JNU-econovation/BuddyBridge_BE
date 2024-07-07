package econo.buddybridge.matching.controller;

import econo.buddybridge.matching.dto.MatchingReqDto;
import econo.buddybridge.matching.service.MatchingService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
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
            @RequestBody MatchingReqDto matchingReqDto
    ){
        matchingService.createMatchingById(matchingReqDto);
        return ApiResponseGenerator.success(1L,HttpStatus.OK);
    }

    // matching 완료 -> DONE 변경, 거절 -> FAILED 변경 // 임시 함수
    @PutMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> updateMatching(){

        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    // matching 완전 삭제 -> 좋은 방법인가 고민 필요 // 임시 함수
    // 실수로 제거한 경우는 채팅방을 다시 만들어야하는지
    @DeleteMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> deleteMatching(){

        return ApiResponseGenerator.success(HttpStatus.OK);
    }


}
