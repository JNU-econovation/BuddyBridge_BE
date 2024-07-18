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
            @Header ("simpSessionAttributes") Map<String, Object> attributes
    ){

        // TODO: 세션 유틸에 타입유형 검사하는 로직 만들기
        Long senderId = null;
        Object memberIdObject = attributes.get("memberId");
        if(memberIdObject instanceof Integer){
            senderId = ((Integer) memberIdObject).longValue();
        } else if(memberIdObject instanceof Long){
            senderId = (Long) memberIdObject;
        } else{
            throw new IllegalArgumentException("적절하지 않은 memberId 입니다.");
        }
        return chatMessageService.save(senderId,chatMessageReqDto,matchingId);
    }

    // 마지막 메시지 1개만 받아오기
//    @GetMapping("/api/queue/chat/{matching-id}")
//    public ApiResponse<ApiResponse.CustomBody<ChatMessageResDto>> getMessages(
//            @PathVariable("matching-id") Long matchingId
//    ){
//        ChatMessageResDto resDto = chatMessageService.getLastChatMessage(matchingId);
//        return ApiResponseGenerator.success(resDto, HttpStatus.OK);
//    }

    //TODO: 채팅 메시지 삭제(업데이트로 MessageType: DELETE로 변경)
}
