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

    public static ReceiverDto from(Member receiver) {
        return ReceiverDto.builder()
                .receiverId(receiver.getId())
                .receiverName(receiver.getName())
                .receiverProfileImg(receiver.getProfileImageUrl())
                .build();
    }
}
