package econo.buddybridge.matching.state;

import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.MemberRole;

public interface MatchingState {
    MatchingStatus getStatus();

    MatchingState handleEvent(MatchingStatusChangeEvent event, MemberRole role);
}
