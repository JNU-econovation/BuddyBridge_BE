package econo.buddybridge.matching.state.impl;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.state.MatchingState;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.MemberRole;
import lombok.Getter;

public class VolunteeringCompletedState implements MatchingState {

    @Getter
    private static final VolunteeringCompletedState instance = new VolunteeringCompletedState();

    private VolunteeringCompletedState() {
    }

    @Override
    public MatchingStatus getStatus() {
        return MatchingStatus.VOLUNTEERING_COMPLETED;
    }

    @Override
    public MatchingState handleEvent(MatchingStatusChangeEvent event, MemberRole role) {
        if (event == MatchingStatusChangeEvent.SUBMIT_VOLUNTEERING_VERIFICATION && role == MemberRole.GIVER) {
            return VolunteeringVerifiedState.getInstance();
        }
        throw new IllegalArgumentException("적절하지 않은 이벤트 혹은 봉사자(GIVER)가 아닙니다.");
    }
}
