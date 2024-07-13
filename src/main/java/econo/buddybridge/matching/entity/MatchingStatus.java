package econo.buddybridge.matching.entity;

import lombok.Getter;


@Getter
public enum MatchingStatus {
// TODO: MatchingStatus->MatchingStatus
    DONE("DONE"),
    FAILED("FAILED"),
    PENDING("PENDING");

    private final String matchingStatus;

    private MatchingStatus(String matchingStatus){
        this.matchingStatus = matchingStatus;
    }
}
