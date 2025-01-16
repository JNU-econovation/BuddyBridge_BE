package econo.buddybridge.member.repository;

import econo.buddybridge.member.dto.MemberListItem;
import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberListItem> findMembers(Integer page, Integer size, String sort);

    Long totalElements();
}
