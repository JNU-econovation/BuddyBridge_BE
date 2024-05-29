package econo.buddybridge.member.service;

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
    public void join(Member member){
        memberRepository.save(member);
    }

}
