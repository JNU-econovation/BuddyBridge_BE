package econo.buddybridge.member.event;

import econo.buddybridge.member.entity.Member;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDeleteEvent {

    private final List<Member> members;

    public static MemberDeleteEvent from(List<Member> members) {
        return new MemberDeleteEvent(members);
    }
}
