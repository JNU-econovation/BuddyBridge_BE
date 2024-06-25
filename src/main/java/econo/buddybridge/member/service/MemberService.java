package econo.buddybridge.member.service;

import econo.buddybridge.auth.dto.OAuthInfoResponse;
import econo.buddybridge.member.dto.MemberDto;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberDto findMember(Long memberId) {
        Member member = validateVerifyMember(memberId);
        return MemberDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .age(member.getAge())
                .gender(member.getGender())
                .disabilityType(member.getDisabilityType())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    // 존재하는 회원인지 확인
    @Transactional(readOnly = true)
    public Member validateVerifyMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public MemberDto findOrCreateMemberByEmail(OAuthInfoResponse info) {
        Member member = memberRepository.findByEmail(info.getEmail())
                .orElseGet(() -> newMember(info));
        return MemberDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .age(member.getAge())
                .gender(member.getGender())
                .disabilityType(member.getDisabilityType())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    private Member newMember(OAuthInfoResponse info) {
        Member member = Member.builder()
                .email(info.getEmail())
                .nickname(info.getNickname())
                .age(info.getAge())
                .gender(Gender.fromEnglishName(info.getGender()))
                .profileImageUrl(info.getProfileImageUrl())
                .build();
        return memberRepository.save(member);
    }
}
