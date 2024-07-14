package econo.buddybridge.matching.controller;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.matching.dto.MatchingResDto;
import econo.buddybridge.matching.service.MatchingRoomService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/matchings")
public class MatchingRoomController {

    private final MatchingRoomService matchingRoomService;

    // GET 로그인한 사용자 매칭방 목록 조회
    @GetMapping("")
    public ApiResponse<ApiResponse.CustomBody<List<MatchingResDto>>> getAllMatchingRooms(
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        List<MatchingResDto> matchingRooms = matchingRoomService.getMatchingRoomsByMemberId(memberId);
        return ApiResponseGenerator.success(matchingRooms, HttpStatus.OK);
    }

    // GET 매칭방 상세 조회(대화 내용 조회)
    @GetMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<List<ChatMessageResDto>>> getChatMessages(
            @PathVariable("matching-id") Long matchingId,
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        List<ChatMessageResDto> resDto = matchingRoomService.getMatchingRoomMessages(matchingId);

        return ApiResponseGenerator.success(resDto,HttpStatus.OK);
    }

}
