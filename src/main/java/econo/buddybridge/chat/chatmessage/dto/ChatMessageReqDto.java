package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;

public record ChatMessageReqDto(
        Long senderId, // 추후 제거
        String content,
        MessageType messageType
) {
}
