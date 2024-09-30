package econo.buddybridge.matching.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record MatchingCustomPage(
        List<MatchingResDto> matchings,
        LocalDateTime cursor,
        Boolean nextPage
) {

}
