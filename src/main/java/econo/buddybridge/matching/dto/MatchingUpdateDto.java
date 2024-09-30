package econo.buddybridge.matching.dto;

import econo.buddybridge.matching.entity.MatchingStatus;

public record MatchingUpdateDto(
        MatchingStatus matchingStatus
) {

}
