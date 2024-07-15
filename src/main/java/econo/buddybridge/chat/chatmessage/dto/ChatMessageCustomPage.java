package econo.buddybridge.chat.chatmessage.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatMessageCustomPage(
        List<ChatMessageResDto> chatMessages,
        Long cursor,
        Boolean nextPage
) {
}
