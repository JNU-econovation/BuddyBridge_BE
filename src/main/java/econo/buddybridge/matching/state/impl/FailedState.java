package econo.buddybridge.matching.state.impl;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.exception.state.InvalidTransitionForFailedException;
import econo.buddybridge.matching.state.MatchingState;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.member.entity.MemberRole;
import lombok.Getter;

public class FailedState implements MatchingState {

    @Getter
    private static final FailedState instance = new FailedState();

    private FailedState() {
    }

    @Override
    public MatchingStatus getStatus() {
        return MatchingStatus.FAILED;
    }

    @Override
    public MatchingState handleEvent(MatchingStatusChangeEvent event, MemberRole role) {
        throw InvalidTransitionForFailedException.EXCEPTION;
    }
}
