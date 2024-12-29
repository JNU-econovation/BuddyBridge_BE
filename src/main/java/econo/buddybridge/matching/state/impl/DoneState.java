package econo.buddybridge.matching.state.impl;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.state.MatchingState;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.MemberRole;
import lombok.Getter;

public class DoneState implements MatchingState {

    @Getter
    private static final DoneState instance = new DoneState();

    private DoneState() {
    }

    @Override
    public MatchingStatus getStatus() {
        return MatchingStatus.DONE;
    }

    @Override
    public MatchingState handleEvent(MatchingStatusChangeEvent event, MemberRole role) {
        switch (event) {
            case TOGGLE_DONE:
                System.out.println("DoneState -> PendingState");
                return PendingState.getInstance();
            case MARK_AS_HELP_NOT_RECEIVED:
                if (role == MemberRole.TAKER) {
                    System.out.println("DoneState -> FailedState");
                    return FailedState.getInstance();
                }
                break;
            case MARK_AS_HELP_RECEIVED:
                if (role == MemberRole.TAKER) {
                    System.out.println("DoneState -> VolunteeringCompletedState");
                    return VolunteeringCompletedState.getInstance();
                }
                break;
        }
        System.out.println("이도저도 아닌 요청 " + event);
        throw new IllegalArgumentException("해당 상태로 전환할 수 없습니다.");
    }
}
