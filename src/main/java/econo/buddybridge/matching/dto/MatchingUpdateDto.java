package econo.buddybridge.matching.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;

public record MatchingUpdateDto(
        @EnumTypeValue(enumClass = MatchingStatusChangeEvent.class)
        String matchingStatusEvent
) {

}
