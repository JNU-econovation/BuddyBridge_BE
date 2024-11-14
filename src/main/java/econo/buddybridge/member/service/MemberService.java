package econo.buddybridge.member.service;

import econo.buddybridge.auth.dto.kakao.UserInfoWithKakaoToken;
import econo.buddybridge.auth.utils.PasswordEncoder;
import econo.buddybridge.member.dto.MemberReqDto;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.dto.MemberSignUpReqDto;
import econo.buddybridge.member.dto.MemberSignUpResDto;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.exception.MemberNotFoundException;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public MemberSignUpResDto findOrCreateSignUpMemberByEmail(MemberSignUpReqDto memberSignUpReqDto) {
        Member member = memberRepository.findByEmail(memberSignUpReqDto.email())
                .orElseGet(() -> newSignUpMember(memberSignUpReqDto));
        return new MemberSignUpResDto(member);
    }

    private Member newSignUpMember(MemberSignUpReqDto memberSignUpReqDto) {
        int age = Period.between(memberSignUpReqDto.birthDate(), LocalDate.now()).getYears();

        // Todo : passwordEncoder를 사용하여 password와 salt를 생성 좋은 패턴일까?
        String password = passwordEncoder.encrypt(memberSignUpReqDto.password()).hashedPassword();
        String salt = passwordEncoder.encrypt(memberSignUpReqDto.password()).salt();

        // Todo : 해당 설계가 올바른지, kakaoToken은 어떻게 처리할지
        Member member = Member.builder()
                .name(memberSignUpReqDto.name())
                .nickname("닉네임을 설정해주세요")
                .profileImageUrl("https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg")
                .email(memberSignUpReqDto.email())
                .age(age)
                .disabilityType(DisabilityType.없음)
                .gender(memberSignUpReqDto.gender())
                .password(password)
                .salt(salt)
                .build();

        return memberRepository.save(member);
    }

}
