package econo.buddybridge.chat.chatroom.dto;

import java.util.List;

public record ChatRoomCustomPage(
        List<ChatRoomResDto> chatRooms,
        Long cursor,
        Boolean nextPage
) {
}
