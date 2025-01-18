package econo.buddybridge.member.repository;

import static econo.buddybridge.blacklist.entity.QBlackList.blackList;
import static econo.buddybridge.member.entity.QMember.member;
import static econo.buddybridge.report.entity.QReport.report;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.member.dto.MemberListItem;
import econo.buddybridge.member.dto.QMemberListItem;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberListItem> findMembers(Integer page, Integer size, String sort) {
        return queryFactory
                .select(new QMemberListItem(
                        member.id,
                        member.name,
                        member.nickname,
                        member.gender,
                        member.age,
                        member.disabilityType,
                        member.email,
                        JPAExpressions
                                .select(report.count())
                                .from(report)
                                .where(report.reported.eq(member)),
                        JPAExpressions
                                .selectOne()
                                .from(blackList)
                                .where(blackList.reportedMember.eq(member))
                                .exists()
                ))
                .from(member)
                .offset((long) page * size)
                .limit(size)
                .orderBy(member.createdAt.desc())
                .fetch();
    }

    @Override
    public Long totalElements() {
        return queryFactory
                .select(member.count())
                .from(member)
                .fetchOne();
    }
}
