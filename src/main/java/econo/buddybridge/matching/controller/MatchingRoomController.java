package econo.buddybridge.matching.controller;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.matching.dto.MatchingCustomPage;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/matchings")
public class MatchingRoomController {

    private final MatchingRoomService matchingRoomService;

    // GET 로그인한 사용자 매칭방 목록 조회 -> 페이지네이션 적용
    @GetMapping("")
    public ApiResponse<ApiResponse.CustomBody<MatchingCustomPage>> getAllMatchingRooms(
            @RequestParam("limit") Integer size,
            @RequestParam(value="cursor", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        MatchingCustomPage matchings = matchingRoomService.getMatchingRoomsByMemberId(memberId, size, cursor);
        return ApiResponseGenerator.success(matchings, HttpStatus.OK);
    }

    // GET 매칭방 상세 조회(대화 내용 조회)
    @GetMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<ChatMessageCustomPage>> getChatMessages(
            @PathVariable("matching-id") Long matchingId,
            @RequestParam("limit") Integer size,
            @RequestParam(value="cursor", required=false) Long cursor,
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        ChatMessageCustomPage chatMessages = matchingRoomService.getMatchingRoomMessages(memberId,matchingId,size,cursor);
        return ApiResponseGenerator.success(chatMessages,HttpStatus.OK);
    }

}
