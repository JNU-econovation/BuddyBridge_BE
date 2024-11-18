package econo.buddybridge.member.service;

import econo.buddybridge.member.dto.EmailReqDto;
import econo.buddybridge.member.dto.NicknameReqDto;
import econo.buddybridge.member.exception.MemberEmailAlreadyExistsException;
import econo.buddybridge.member.exception.MemberNicknameAlreadyExistsException;
import econo.buddybridge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DuplicationCheckService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public void checkEmail(EmailReqDto emailReqDto) {
        if (memberRepository.existsByEmail(emailReqDto.email())) {
            throw MemberEmailAlreadyExistsException.EXCEPTION;
        }
    }

    @Transactional(readOnly = true)
    public void checkNickname(NicknameReqDto nicknameReqDto) {
        if (memberRepository.existsByNickname(nicknameReqDto.nickname())) {
            throw MemberNicknameAlreadyExistsException.EXCEPTION;
        }
    }
}
