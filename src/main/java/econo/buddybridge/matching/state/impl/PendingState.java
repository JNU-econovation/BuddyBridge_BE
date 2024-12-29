package econo.buddybridge.matching.state.impl;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.exception.state.InvalidTransitionToDoneException;
import econo.buddybridge.matching.state.MatchingState;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.MemberRole;
import lombok.Getter;

public class PendingState implements MatchingState {

    @Getter
    private static final PendingState instance = new PendingState();

    private PendingState() {
    }

    @Override
    public MatchingStatus getStatus() {
        return MatchingStatus.PENDING;
    }

    @Override
    public MatchingState handleEvent(MatchingStatusChangeEvent event, MemberRole memberRole) {
        if (event == MatchingStatusChangeEvent.TOGGLE_DONE) {
            return DoneState.getInstance();
        }
        throw InvalidTransitionToDoneException.EXCEPTION;
    }
}
