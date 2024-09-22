package econo.buddybridge.chat.chatmessage.controller;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{matching-id}") // 메시지 보내기 // /api/app/chat/{room-id} - pub
    @SendTo("/api/queue/chat/{matching-id}") // 구독 경로 - sub
    public ChatMessageResDto sendMessage(
            @DestinationVariable("matching-id") Long matchingId,
            @Payload ChatMessageReqDto chatMessageReqDto,
            @Header("simpSessionAttributes") Map<String, Object> attributes
    ) {
        Object memberIdObject = attributes.get("memberId");
        Long senderId = Long.parseLong(memberIdObject.toString());
        return chatMessageService.save(senderId, chatMessageReqDto, matchingId);
    }
}
