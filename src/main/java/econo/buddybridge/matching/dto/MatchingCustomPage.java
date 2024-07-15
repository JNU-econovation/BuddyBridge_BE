package econo.buddybridge.matching.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MatchingCustomPage(
        List<MatchingResDto> matchings,
        LocalDateTime cursor,
        Boolean nextPage
) {
}
