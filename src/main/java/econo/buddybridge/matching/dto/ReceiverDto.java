package econo.buddybridge.matching.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.member.entity.Member;
import lombok.Builder;

@Builder
public record ReceiverDto(
        Long receiverId,
        String receiverName,
        String receiverProfileImg
) {

    @QueryProjection
    public ReceiverDto {
    }

    public ReceiverDto(Member receiver) {
        this(receiver.getId(), receiver.getName(), receiver.getProfileImageUrl());
    }
}
