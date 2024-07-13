package econo.buddybridge.chat.chatroom.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.comment.dto.AuthorDto;
import econo.buddybridge.matching.entity.MatchingStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatRoomResDto(
        Long chatRoomId,
        String lastMessage,
        LocalDateTime lastMessageTime,
        MatchingStatus matchingStatus,
        AuthorDto authorDto
) {

    @QueryProjection
    public ChatRoomResDto{
    }
}
