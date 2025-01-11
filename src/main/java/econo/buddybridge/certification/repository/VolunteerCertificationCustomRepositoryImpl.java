package econo.buddybridge.certification.repository;

import static econo.buddybridge.certification.entity.QVolunteerCertification.volunteerCertification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.certification.dto.QVolunteerCertificationListItem;
import econo.buddybridge.certification.dto.VolunteerCertificationCustomPage;
import econo.buddybridge.certification.dto.VolunteerCertificationListItem;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VolunteerCertificationCustomRepositoryImpl implements VolunteerCertificationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public VolunteerCertificationCustomPage findVolunteerCertifications(Integer page, Integer size, String sort) {
        List<VolunteerCertificationListItem> content = queryFactory
                .select(new QVolunteerCertificationListItem(
                        volunteerCertification.id,
                        volunteerCertification.matching.giver.name,
                        volunteerCertification.matching.giver.email,
                        volunteerCertification.matching.post.id,
                        volunteerCertification.matching.post.postType,
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
