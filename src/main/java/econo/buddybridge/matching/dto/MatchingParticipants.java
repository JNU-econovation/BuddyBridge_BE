package econo.buddybridge.matching.dto;

import econo.buddybridge.member.entity.Member;

public record MatchingParticipants(
        Member taker,
        Member giver
) {

}
