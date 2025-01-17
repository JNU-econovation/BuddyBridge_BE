package econo.buddybridge.blacklist.service;

import econo.buddybridge.blacklist.dto.BlackListRequest;
import econo.buddybridge.blacklist.entity.BlackList;
import econo.buddybridge.blacklist.repository.BlackListRepository;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlackListService {

    private final MemberService memberService;
    private final BlackListRepository blackListRepository;

    @Transactional
    public void registerBlackListMember(BlackListRequest request) {
        Member reportedMember = memberService.findMemberByIdOrThrow(request.reportedMemberId());

        if (isBlackListed(request.reportedMemberId())) {
            throw new IllegalArgumentException("이미 블랙리스트에 등록된 회원입니다.");
        }

        BlackList blackList = BlackList.builder()
                .reportedMember(reportedMember)
                .build();

        blackListRepository.save(blackList);
    }

    @Transactional
    public void deleteBlackListMember(BlackListRequest request) {
        Member reportedMember = memberService.findMemberByIdOrThrow(request.reportedMemberId());

        if (!isBlackListed(request.reportedMemberId())) {
            throw new IllegalArgumentException("블랙리스트에 등록되지 않은 회원입니다.");
        }

        blackListRepository.deleteByReportedMember(reportedMember);
    }

    @Transactional
    public boolean isBlackListed(Long reportedMemberId) {
        Member reportedMember = memberService.findMemberByIdOrThrow(reportedMemberId);
        return blackListRepository.existsByReportedMember(reportedMember);
    }
}
