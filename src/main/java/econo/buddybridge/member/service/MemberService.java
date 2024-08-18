package econo.buddybridge.member.service;

import econo.buddybridge.auth.dto.OAuthInfoResponse;
import econo.buddybridge.member.dto.MemberReqDto;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResDto findMemberById(Long memberId) {
        Member member = validateVerifyMemberById(memberId);
        return new MemberResDto(member);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    // 존재하는 회원인지 확인
    public Member validateVerifyMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public MemberResDto findOrCreateMemberByEmail(OAuthInfoResponse info) {
        Member member = memberRepository.findByEmail(info.getEmail())
                .orElseGet(() -> newMember(info));
        return new MemberResDto(member);
    }

    private Member newMember(OAuthInfoResponse info) {
        Member member = info.toMember();
        return memberRepository.save(member);
    }

    @Transactional
    public void updateMemberById(Long memberId, MemberReqDto memberReqDto) {
        Member member = validateVerifyMemberById(memberId);

        member.updateMemberInfo(memberReqDto.name(), memberReqDto.nickname(), memberReqDto.profileImageUrl(),
                memberReqDto.email(), memberReqDto.age(), memberReqDto.disabilityType(), member.getGender());
    }
}
