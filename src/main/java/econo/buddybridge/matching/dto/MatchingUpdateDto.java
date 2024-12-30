package econo.buddybridge.matching.dto;

import econo.buddybridge.matching.state.MatchingStatusChangeEvent;

public record MatchingUpdateDto(
        MatchingStatusChangeEvent matchingStatusEvent
) {

}
