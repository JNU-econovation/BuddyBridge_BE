package econo.buddybridge.chat.chatmessage.controller;

import econo.buddybridge.auth.resolver.MemberTokenId;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.service.ChatMessageService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.websocket.WebSocketPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{matching-id}") // 메시지 보내기 // /api/app/chat/{room-id} - pub
    @SendTo("/api/queue/chat/{matching-id}") // 구독 경로 - sub
    public ChatMessageResDto sendMessage(
            @DestinationVariable("matching-id") Long matchingId,
            @Payload ChatMessageReqDto chatMessageReqDto,
            Principal principal
    ) {
        WebSocketPrincipal webSocketPrincipal = (WebSocketPrincipal) principal;
        Long senderId = webSocketPrincipal.getSenderId();
        return chatMessageService.save(senderId, chatMessageReqDto, matchingId);
    }

    @Operation(summary = "봉사 인증 요청 문자 전송", description = "Giver(봉사자)가 봉사를 완료한 후 TAKER(수혜자)에 봉사 인증 요청을 부탁하는 문자를 전송합니다.")
    @PostMapping("/api/v1/matchings/{matching-id}/certification-requests")
    public ApiResponse<CustomBody<ChatMessageResDto>> sendVolunteerCompletionRequest(
            @PathVariable("matching-id") Long matchingId,
            @Parameter(hidden = true) @MemberTokenId Long memberId
    ) {
        ChatMessageResDto chatMessage = chatMessageService.sendVolunteerCompletionRequest(matchingId, memberId);
        return ApiResponseGenerator.success(chatMessage, HttpStatus.OK);
    }
}
