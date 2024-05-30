package econo.buddybridge.member.service;

import econo.buddybridge.member.dto.MemberDto;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// 수정 필요
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void join(MemberDto memberDto){
        Member member = Member.builder()
            .nickname(memberDto.nickname())
            .email(memberDto.email())
            .age(memberDto.age())
            .profileImageUrl(memberDto.profileImageUrl())
            .build();
        memberRepository.save(member);
    }

}
