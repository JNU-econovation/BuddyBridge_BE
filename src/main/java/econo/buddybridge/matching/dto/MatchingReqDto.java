package econo.buddybridge.matching.dto;

public record MatchingReqDto(
    Long postId,
    Long takerId,
    Long giverId
) {
}
