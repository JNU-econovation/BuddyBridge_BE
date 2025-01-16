package econo.buddybridge.member.dto;

import java.util.List;

public record MemberCustomPage(
        List<MemberListItem> content,
        Long totalElements,
        Boolean last
) {

}
