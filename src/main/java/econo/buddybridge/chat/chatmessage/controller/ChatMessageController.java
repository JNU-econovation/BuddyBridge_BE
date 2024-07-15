package econo.buddybridge.chat.chatmessage.controller;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.service.ChatMessageService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{matching-id}") // 메시지 보내기 // /api/app/chat/{room-id} - pub
    @SendTo("/api/queue/chat/{matching-id}") // 구독 경로 - sub
    public ChatMessageResDto sendMessage(
            @DestinationVariable("matching-id") Long matchingId,
            @Payload ChatMessageReqDto chatMessageReqDto
            // HttpServletRequest request
            ){
        return chatMessageService.save(chatMessageReqDto,matchingId);
    }

    // 마지막 메시지 1개만 받아오기
    @GetMapping("/api/queue/chat/{matching-id}")
    public ApiResponse<ApiResponse.CustomBody<ChatMessageResDto>> getMessages(
            @PathVariable("matching-id") Long matchingId
    ){
        ChatMessageResDto resDto = chatMessageService.getLastChatMessage(matchingId);
        return ApiResponseGenerator.success(resDto, HttpStatus.OK);
    }
}
