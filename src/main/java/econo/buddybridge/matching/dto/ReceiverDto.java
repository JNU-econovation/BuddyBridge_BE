package econo.buddybridge.matching.dto;

import lombok.Builder;

@Builder
public record ReceiverDto(
        Long receiverId,
        String receiverName,
        String receiverProfileImg
) {
}
