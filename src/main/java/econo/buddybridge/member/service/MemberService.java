package econo.buddybridge.member.service;

import econo.buddybridge.auth.dto.kakao.UserInfoWithKakaoToken;
import econo.buddybridge.member.dto.MemberReqDto;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.exception.MemberNotFoundException;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResDto findMemberById(Long memberId) {
        Member member = findMemberByIdOrThrow(memberId);
        return new MemberResDto(member);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    // 존재하는 회원인지 확인
    @Transactional(readOnly = true)
    public Member findMemberByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
    }

    @Transactional
    public MemberResDto findOrCreateMemberByEmail(UserInfoWithKakaoToken userInfo) {
        Member member = memberRepository.findByEmail(userInfo.info().getEmail())
                .orElseGet(() -> newMember(userInfo));
        return new MemberResDto(member);
    }

    private Member newMember(UserInfoWithKakaoToken userInfo) {
        Member member = userInfo.info().toMember();
        member.updateKakaoToken(userInfo.accessToken());
        return memberRepository.save(member);
    }

    @Transactional
    public void updateMemberById(Long memberId, MemberReqDto memberReqDto) {
        Member member = findMemberByIdOrThrow(memberId);

        member.updateMemberInfo(memberReqDto.name(), memberReqDto.nickname(), memberReqDto.profileImageUrl(),
                memberReqDto.email(), memberReqDto.age(), memberReqDto.disabilityType(), member.getGender());
    }
}
