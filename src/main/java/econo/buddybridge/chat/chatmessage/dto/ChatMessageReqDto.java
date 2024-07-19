package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;

public record ChatMessageReqDto(
        String content,
        MessageType messageType
) {
}
