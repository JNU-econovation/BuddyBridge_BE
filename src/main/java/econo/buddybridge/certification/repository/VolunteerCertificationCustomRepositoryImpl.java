package econo.buddybridge.certification.repository;

import static econo.buddybridge.certification.entity.QVolunteerCertification.volunteerCertification;
import static econo.buddybridge.matching.entity.QMatching.matching;
import static econo.buddybridge.post.entity.QPost.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.certification.dto.AdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.QAdminCertificationAuthorDetailResponse;
import econo.buddybridge.certification.dto.QAdminCertificationDetailResponse;
import econo.buddybridge.certification.dto.QAdminCertificationPostDetailResponse;
import econo.buddybridge.certification.dto.QAdminVolunteerCertificationDetailResponse;
import econo.buddybridge.certification.dto.QVolunteerCertificationListItem;
import econo.buddybridge.certification.dto.QVolunteerCertificationResponse;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationListItem;
import econo.buddybridge.certification.dto.VolunteerCertificationResponse;
import econo.buddybridge.certification.entity.VolunteerCertification;
import econo.buddybridge.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VolunteerCertificationCustomRepositoryImpl implements VolunteerCertificationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
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
                .from(volunteerCertification)
                .leftJoin(volunteerCertification.matching, matching)
                .where(volunteerCertification.eq(certification).and(volunteerCertification.matching.giver.eq(member)))
                .fetchOne();
    }

    @Override
    public AdminVolunteerCertificationDetailResponse findAdminVolunteerCertification(VolunteerCertification certification) {
        return queryFactory
                .select(new QAdminVolunteerCertificationDetailResponse(
                                new QAdminCertificationAuthorDetailResponse(
                                        post.author.name,
                                        post.author.nickname,
                                        post.author.gender,
                                        post.author.age,
                                        post.author.disabilityType
                                ),
                                new QAdminCertificationPostDetailResponse(
                                        post.title,
                                        post.district,
                                        post.schedule.startDate,
                                        post.schedule.endDate,
                                        post.schedule.scheduleType,
                                        post.schedule.scheduleDetails,
                                        post.assistanceTime.assistanceStartTime,
                                        post.assistanceTime.assistanceEndTime,
                                        post.content
                                ),
                                new QAdminCertificationDetailResponse(
                                        volunteerCertification.id,
                                        volunteerCertification.matching.giver.nickname,
                                        volunteerCertification.createdAt,
                                        volunteerCertification.matching.giver.name,
                                        volunteerCertification.matching.giver.email,
                                        volunteerCertification.matching.post.id,
                                        volunteerCertification.matching.post.postType,
                                        volunteerCertification.volunteerTime.volunteerDate,
                                        volunteerCertification.matching.post.assistanceType,
                                        volunteerCertification.volunteerTime.startTime,
                                        volunteerCertification.volunteerTime.endTime,
                                        volunteerCertification.content,
                                        volunteerCertification.isCertified
                                )
                        )
                )
                .from(volunteerCertification)
                .leftJoin(volunteerCertification.matching.post, post)
                .where(volunteerCertification.eq(certification))
                .fetchOne();
    }

    @Override
    public VolunteerCertificationCustomPage findAdminVolunteerCertifications(Integer page, Integer size, String sort) {
        List<VolunteerCertificationListItem> content = queryFactory
                .select(new QVolunteerCertificationListItem(
                        volunteerCertification.id,
                        volunteerCertification.matching.giver.name,
                        volunteerCertification.matching.giver.email,
                        volunteerCertification.matching.post.id,
                        volunteerCertification.matching.post.postType,
                        volunteerCertification.isCertified,
                        volunteerCertification.createdAt
                ))
                .from(volunteerCertification)
                .offset((long) page * size)
                .limit(size)
                .orderBy(volunteerCertification.createdAt.desc())
                .fetch();

        Long totalElements = queryFactory
                .select(volunteerCertification.count())
                .from(volunteerCertification)
                .fetchOne();

        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        return new VolunteerCertificationCustomPage(content, totalElements, last);
    }
}
