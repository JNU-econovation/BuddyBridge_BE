package econo.buddybridge.matching.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageCustomPage;
import econo.buddybridge.matching.dto.MatchingCustomPage;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.service.MatchingRoomService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/matchings")
@Tag(name = "채팅방 API", description = "채팅방 관련 API")
public class MatchingRoomController {

    private final MatchingRoomService matchingRoomService;

    // 매칭 상태 변경되면 -> 게시글 상태 변경되도록

    // 채팅방 목록 조회
    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<ApiResponse.CustomBody<MatchingCustomPage>> getAllMatchingRooms(
            @RequestParam("limit") Integer size,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(value = "matching-status", required = false) MatchingStatus matchingStatus,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        MatchingCustomPage matchings = matchingRoomService.getMatchings(memberId, size, cursor, matchingStatus);
        return ApiResponseGenerator.success(matchings, HttpStatus.OK);
    }

    // GET 채팅방 상세 조회(대화 내용 조회)
    @Operation(summary = "채팅방 대화 내용 조회", description = "채팅방 대화 내용을 조회합니다.")
    @GetMapping("/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<ChatMessageCustomPage>> getChatMessages(
            @PathVariable("matching-id") Long matchingId,
            @RequestParam("limit") Integer size,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        ChatMessageCustomPage chatMessages = matchingRoomService.getMatchingRoomMessages(memberId, matchingId, size, cursor);
        return ApiResponseGenerator.success(chatMessages, HttpStatus.OK);
    }
}
