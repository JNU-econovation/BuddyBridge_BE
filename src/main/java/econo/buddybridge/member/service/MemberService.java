package econo.buddybridge.member.service;

import econo.buddybridge.auth.dto.LoginReqDto;
import econo.buddybridge.auth.dto.PasswordHashDto;
import econo.buddybridge.auth.dto.kakao.UserInfoWithKakaoToken;
import econo.buddybridge.auth.utils.PasswordEncoder;
import econo.buddybridge.member.dto.MemberReqDto;
import econo.buddybridge.member.dto.MemberResDto;
import econo.buddybridge.member.dto.MemberSignUpReqDto;
import econo.buddybridge.member.dto.MemberSignUpResDto;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.member.exception.InvalidPasswordOrEmailException;
import econo.buddybridge.member.exception.MemberEmailAlreadyExistsException;
import econo.buddybridge.member.exception.MemberNicknameAlreadyExistsException;
import econo.buddybridge.member.exception.MemberNotFoundException;
import econo.buddybridge.member.repository.MemberRepository;
import java.time.LocalDate;
import java.time.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (memberRepository.existsByNicknameAndIdNot(memberReqDto.nickname(), memberId)) {
            throw MemberNicknameAlreadyExistsException.EXCEPTION;
        }

        member.updateMemberInfo(memberReqDto.nickname(), memberReqDto.disabilityType());
    }

    @Transactional
    public MemberSignUpResDto createSignUpMember(MemberSignUpReqDto memberSignUpReqDto) {
        boolean existsByEmail = memberRepository.existsByEmail(memberSignUpReqDto.email());
        boolean existsByNickname = memberRepository.existsByNickname(memberSignUpReqDto.nickname());

        if (existsByEmail) {
            throw MemberEmailAlreadyExistsException.EXCEPTION;
        }

        if (existsByNickname) {
            throw MemberNicknameAlreadyExistsException.EXCEPTION;
        }

        signUpMember(memberSignUpReqDto);
        return new MemberSignUpResDto("회원가입에 성공하셨습니다.");
    }

    private void signUpMember(MemberSignUpReqDto memberSignUpReqDto) {
        int age = Period.between(memberSignUpReqDto.birthDate(), LocalDate.now()).getYears();

        PasswordHashDto passwordHashDto = passwordEncoder.encrypt(memberSignUpReqDto.password());
        String password = passwordHashDto.hashedPassword();
        String salt = passwordHashDto.salt();

        Member member = Member.builder()
                .name(memberSignUpReqDto.name())
                .nickname(memberSignUpReqDto.nickname())
                .profileImageUrl("https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg")
                .email(memberSignUpReqDto.email())
                .age(age)
                .disabilityType(DisabilityType.없음)
                .gender(memberSignUpReqDto.gender())
                .role(Role.USER)
                .password(password)
                .salt(salt)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public MemberResDto findMemberByEmailAndPassword(LoginReqDto loginReqDto) {
        Member member = memberRepository.findByEmail(loginReqDto.email())
                .orElseThrow(() -> InvalidPasswordOrEmailException.EXCEPTION);

        if (!passwordEncoder.verify(loginReqDto.password(), member.getPassword(), member.getSalt())) {
            throw InvalidPasswordOrEmailException.EXCEPTION;
        }

        return new MemberResDto(member);
    }
}
