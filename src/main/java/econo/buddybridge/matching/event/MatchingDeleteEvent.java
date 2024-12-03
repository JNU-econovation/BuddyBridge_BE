package econo.buddybridge.matching.event;

import econo.buddybridge.common.event.DomainEvent;
import econo.buddybridge.matching.entity.Matching;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchingDeleteEvent extends DomainEvent {

    private final Matching matching;

    public static MatchingDeleteEvent from(Matching matching) {
        return new MatchingDeleteEvent(matching);
    }
}
