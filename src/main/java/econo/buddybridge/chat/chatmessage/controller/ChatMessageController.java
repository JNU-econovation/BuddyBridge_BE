package econo.buddybridge.chat.chatmessage.controller;

import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.service.ChatMessageService;
import econo.buddybridge.chat.chatmessage.service.MessageReadStatusService;
import econo.buddybridge.websocket.WebSocketPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final MessageReadStatusService messageReadStatusService;

    @MessageMapping("/chat/{matching-id}") // 메시지 보내기 // /api/app/chat/{room-id} - pub
    @SendTo("/api/queue/chat/{matching-id}") // 구독 경로 - sub
    public ChatMessageResDto sendMessage(
            @DestinationVariable("matching-id") Long matchingId,
            @Payload ChatMessageReqDto chatMessageReqDto,
            Principal principal
    ) {
        WebSocketPrincipal webSocketPrincipal = (WebSocketPrincipal) principal;
        Long senderId = webSocketPrincipal.getSenderId();

        messageReadStatusService.updateLastReadTime(matchingId, senderId);

        return chatMessageService.save(senderId, chatMessageReqDto, matchingId);
    }
}
