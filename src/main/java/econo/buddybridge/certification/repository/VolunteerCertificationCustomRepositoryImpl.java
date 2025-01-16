package econo.buddybridge.certification.repository;

import static econo.buddybridge.certification.entity.QVolunteerCertification.volunteerCertification;
import static econo.buddybridge.matching.entity.QMatching.matching;
import static econo.buddybridge.post.entity.QPost.post;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.QAdminCertificationDetailResponse;
import econo.buddybridge.certification.dto.QAdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.QVolunteerCertificationListItem;
import econo.buddybridge.certification.dto.QVolunteerCertificationResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationListItem;
import econo.buddybridge.certification.dto.VolunteerCertificationResponse;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.dto.QAssistanceResDto;
import econo.buddybridge.post.dto.QPostAuthorDto;
import econo.buddybridge.post.dto.QPostDetailDto;
import econo.buddybridge.post.dto.QPostDetailInfoDto;
import econo.buddybridge.post.dto.QScheduleDetailResDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VolunteerCertificationCustomRepositoryImpl implements VolunteerCertificationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override // 사용자 인증 폼 상세 조회
    public VolunteerCertificationResponse findVolunteerCertificationByMemberAndVolunteerCertification(Member member, VolunteerCertification certification) {
        return queryFactory
                .select(new QVolunteerCertificationResponse(
                        volunteerCertification.id,
                        matching.giver.name,
                        matching.giver.email,
                        matching.post.id,
                        matching.post.postType,
                        volunteerCertification.volunteerTime.volunteerDate,
                        matching.post.assistanceType,
                        volunteerCertification.volunteerTime.startTime,
                        volunteerCertification.volunteerTime.endTime,
                        volunteerCertification.content
                ))
                .from(matching)
                .leftJoin(matching.volunteerCertification, volunteerCertification)
                .where(volunteerCertification.eq(certification).and(matching.giver.eq(member)))
                .fetchOne();
    }

    @Override // 관리자 인증 폼 상세 조회
    public AdminVolunteerCertificationDetailResponse findAdminVolunteerCertification(VolunteerCertification certification) {
        return queryFactory
                .select(new QAdminVolunteerCertificationDetailResponse(
                                new QPostDetailDto(
                                        new QPostAuthorDto(
                                                post.author.id,
                                                post.author.nickname,
                                                post.author.profileImageUrl,
                                                post.author.age,
                                                post.author.gender,
                                                post.author.disabilityType
                                        ),
                                        new QPostDetailInfoDto(
                                                post.id,
                                                post.title,
                                                new QScheduleDetailResDto(
                                                        post.schedule.startDate,
                                                        post.schedule.endDate,
                                                        post.schedule.scheduleType,
                                                        post.schedule.scheduleDetails
                                                ),
                                                post.district,
                                                post.content,
                                                post.postType,
                                                post.createdAt,
                                                new QAssistanceResDto(
                                                        post.assistanceType,
                                                        post.assistanceTime.assistanceStartTime,
                                                        post.assistanceTime.assistanceEndTime
                                                ),
                                                Expressions.constant(PostStatus.FINISHED), // 관리자 페이지에서는 항상 모집 완료
                                                Expressions.constant(false) // 관리자 페이지에서는 항상 좋아요 여부가 없음
                                        )
                                ),
                                new QAdminCertificationDetailResponse(
                                        volunteerCertification.id,
                                        matching.giver.nickname,
                                        volunteerCertification.createdAt,
                                        matching.giver.name,
                                        matching.giver.email,
                                        matching.post.id,
                                        matching.post.postType,
                                        volunteerCertification.volunteerTime.volunteerDate,
                                        matching.post.assistanceType,
                                        volunteerCertification.volunteerTime.startTime,
                                        volunteerCertification.volunteerTime.endTime,
                                        volunteerCertification.content,
                                        volunteerCertification.isCertified
                                )
                        )
                )
                .from(matching)
                .leftJoin(matching.volunteerCertification, volunteerCertification)
                .leftJoin(matching.post, post)
                .where(volunteerCertification.eq(certification))
                .fetchOne();
    }

    @Override // 관리자 인증 폼 전체 조회
    public VolunteerCertificationCustomPage findAdminVolunteerCertifications(Integer page, Integer size, String sort) {
        List<VolunteerCertificationListItem> content = queryFactory
                .select(new QVolunteerCertificationListItem(
                        volunteerCertification.id,
                        matching.giver.name,
                        matching.giver.email,
                        matching.post.id,
                        matching.post.postType,
                        volunteerCertification.isCertified,
                        volunteerCertification.createdAt
                ))
                .from(matching)
                .leftJoin(matching.volunteerCertification, volunteerCertification)
                .where(matching.volunteerCertification.isNotNull())
                .offset((long) page * size)
                .limit(size)
                .orderBy(volunteerCertification.createdAt.desc())
                .fetch();

        Long totalElements = queryFactory
                .select(matching.volunteerCertification.count())
                .from(matching)
                .where(matching.volunteerCertification.isNotNull())
                .fetchOne();

        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        return new VolunteerCertificationCustomPage(content, totalElements, last);
    }
}
