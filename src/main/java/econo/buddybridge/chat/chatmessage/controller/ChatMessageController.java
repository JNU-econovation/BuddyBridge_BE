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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{room-id}") // 메시지 보내기 // /api/app/chat/{room-id} - pub
    @SendTo("/api/queue/chat/{room-id}") // 구독 경로 - sub
    public ChatMessageResDto sendMessage(
            @DestinationVariable("room-id") Long chatRoomId,
            @Payload ChatMessageReqDto chatMessageReqDto
            ){
        return chatMessageService.save(chatMessageReqDto,chatRoomId);
    }

    @GetMapping("/api/queue/chat/{room-id}")
    public ApiResponse<ApiResponse.CustomBody<List<ChatMessageResDto>>> getMessages(
            @PathVariable("room-id") Long chatRoomId
    ){
        List<ChatMessageResDto> resDto = chatMessageService.getChatMessages(chatRoomId);
        return ApiResponseGenerator.success(resDto, HttpStatus.OK);
    }
}
