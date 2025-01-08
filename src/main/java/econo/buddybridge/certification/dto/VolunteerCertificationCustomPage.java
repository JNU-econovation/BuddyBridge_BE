package econo.buddybridge.certification.dto;

import java.util.List;

public record VolunteerCertificationCustomPage(
        List<VolunteerCertificationListItem> content,
        Long totalElements,
        Boolean last
) {

}
