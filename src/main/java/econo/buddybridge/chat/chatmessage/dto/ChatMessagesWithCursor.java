package econo.buddybridge.chat.chatmessage.dto;

import java.util.List;

public record ChatMessagesWithCursor(
        List<ChatMessageResDto> chatMessages,
        Long cursor,
        boolean nextPage
) {

}
