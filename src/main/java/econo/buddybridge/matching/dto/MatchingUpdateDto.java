package econo.buddybridge.matching.dto;

import econo.buddybridge.common.validation.EnumTypeValue;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import jakarta.validation.constraints.AssertFalse;

public record MatchingUpdateDto(
        @EnumTypeValue(enumClass = MatchingStatusChangeEvent.class, message = "올바른 매칭 상태 변경 이벤트를 요청해주세요.")
        String matchingStatusEvent
) {

    @AssertFalse(message = "SUBMIT_VOLUNTEERING_VERIFICATION 이벤트는 불가능합니다.")
    private boolean isSubmitVolunteeringVerification() {
        return matchingStatusEvent.equals(MatchingStatusChangeEvent.SUBMIT_VOLUNTEERING_VERIFICATION.getValue());
    }
}
