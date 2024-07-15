package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;

public record ChatMessageReqDto(
        Long senderId, // 추후 제거 -> 로그인한 사용자로 추출
        String content,
        MessageType messageType
) {
}
