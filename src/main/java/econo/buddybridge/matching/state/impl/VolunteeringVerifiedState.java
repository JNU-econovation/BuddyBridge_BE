package econo.buddybridge.matching.state.impl;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.state.MatchingState;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.MemberRole;
import lombok.Getter;

public class VolunteeringVerifiedState implements MatchingState {

    @Getter
    private static final VolunteeringVerifiedState instance = new VolunteeringVerifiedState();

    private VolunteeringVerifiedState() {
    }

    @Override
    public MatchingStatus getStatus() {
        return MatchingStatus.VOLUNTEERING_VERIFIED;
    }

    @Override
    public MatchingState handleEvent(MatchingStatusChangeEvent event, MemberRole role) {
        throw new IllegalStateException("봉사 활동이 정상적으로 완료되었습니다.");
    }
}
