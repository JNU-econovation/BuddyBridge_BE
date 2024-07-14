package econo.buddybridge.matching.dto;

import java.util.List;

public record MatchingCustomPage(
        List<MatchingResDto> matchings,
        Long cursor,
        Boolean nextPage
) {
}
