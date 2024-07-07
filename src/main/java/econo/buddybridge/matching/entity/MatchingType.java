package econo.buddybridge.matching.entity;

import lombok.Getter;

@Getter
public enum MatchingType {
    DONE("DONE"),
    FAILED("FAILED"),
    PENDING("PENDING");

    private final String matchingType;

    private MatchingType(String matchingType){
        this.matchingType = matchingType;
    }
}
