package econo.buddybridge.chat.chatmessage.controller;

import econo.buddybridge.auth.jwt.service.JwtTokenProvider;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageReqDto;
import econo.buddybridge.chat.chatmessage.dto.ChatMessageResDto;
import econo.buddybridge.chat.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("/chat/{matching-id}") // 메시지 보내기 // /api/app/chat/{room-id} - pub
    @SendTo("/api/queue/chat/{matching-id}") // 구독 경로 - sub
    public ChatMessageResDto sendMessage(
            @DestinationVariable("matching-id") Long matchingId,
            @Payload ChatMessageReqDto chatMessageReqDto,
            @Header("Authorization") String header
    ) {
        String accessToken = jwtTokenProvider.extractToken(header);
        Long senderId = jwtTokenProvider.getMemberIdFromAccessToken(accessToken);
        return chatMessageService.save(senderId, chatMessageReqDto, matchingId);
    }
}
