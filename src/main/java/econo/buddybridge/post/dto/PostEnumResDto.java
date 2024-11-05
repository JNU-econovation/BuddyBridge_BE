package econo.buddybridge.post.dto;

import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import java.util.List;
import lombok.Builder;

@Builder
public record PostEnumResDto(
        List<AssistanceType> assistanceTypes,
        List<DisabilityType> disabilityTypes,
        List<District> districts
) {

}
