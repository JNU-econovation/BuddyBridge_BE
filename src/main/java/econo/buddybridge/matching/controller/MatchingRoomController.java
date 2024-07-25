package econo.buddybridge.matching.controller;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.service.MatchingRoomService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

// TODO: 쿼리변수로 매칭 상태받아와 해당 매칭만 표현가능한 API로 변경
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/matchings")
public class MatchingRoomController {

    private final MatchingRoomService matchingRoomService;
    
    // 매칭 상태 변경되면 -> 게시글 상태 변경되도록
    
    // 매칭방 목록 조회
    @GetMapping("")
    public ApiResponse<ApiResponse.CustomBody<MatchingCustomPage>> getAllMatchingRooms(
            @RequestParam("limit") Integer size,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            HttpServletRequest request,
            @RequestParam(value = "matching-status", required = false) MatchingStatus matchingStatus
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        MatchingCustomPage matchings = matchingRoomService.getMatchings(memberId, size, cursor, matchingStatus);
        return ApiResponseGenerator.success(matchings, HttpStatus.OK);
    }

    // GET 매칭방 상세 조회(대화 내용 조회)
    @GetMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<ChatMessageCustomPage>> getChatMessages(
            @PathVariable("matching-id") Long matchingId,
            @RequestParam("limit") Integer size,
            @RequestParam(value="cursor", required=false) Long cursor,
            HttpServletRequest request
    ) {
        Long memberId = SessionUtils.getMemberId(request);
        ChatMessageCustomPage chatMessages = matchingRoomService.getMatchingRoomMessages(memberId, matchingId, size, cursor);
        return ApiResponseGenerator.success(chatMessages, HttpStatus.OK);
    }

}
