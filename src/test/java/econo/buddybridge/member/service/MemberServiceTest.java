package econo.buddybridge.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import econo.buddybridge.member.dto.MemberCustomPage;
import econo.buddybridge.member.dto.MemberListItem;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("회원 서비스 테스트")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원 목록 조회")
    void getMembers() {
        //given
        Integer page = 0;
        Integer size = 10;
        String sort = "desc";

        //when
        MemberCustomPage members = memberService.getMembers(page, size, sort);

        //then
        List<MemberListItem> content = members.content();
        assertThat(content).hasSizeLessThan(size + 1);
    }
}
