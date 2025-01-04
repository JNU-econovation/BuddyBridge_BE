package econo.buddybridge.matching.entity;

import lombok.Getter;

import java.util.List;


@Getter
public enum MatchingStatus {
    DONE("DONE"),
    FAILED("FAILED"),
    PENDING("PENDING"),
    VOLUNTEERING_COMPLETED("VOLUNTEERING_COMPLETED"),
    VOLUNTEERING_VERIFIED("VOLUNTEERING_VERIFIED");

    private final String matchingStatus;

    MatchingStatus(String matchingStatus) {
        this.matchingStatus = matchingStatus;
    }

    public static List<MatchingStatus> getCompletedStatuses() {
        return List.of(DONE, VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED);
    }
}
