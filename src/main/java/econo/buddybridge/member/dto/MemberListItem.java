package econo.buddybridge.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;

public record MemberListItem(
        Long id,
        String name,
        String nickname,
        Gender gender,
        Integer age,
        DisabilityType disabilityType,
        String email,
        Long reportedCount
) {

    @QueryProjection
    public MemberListItem {
    }
}
