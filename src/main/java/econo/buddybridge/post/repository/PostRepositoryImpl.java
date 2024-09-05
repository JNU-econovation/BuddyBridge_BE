package econo.buddybridge.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static econo.buddybridge.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PostCustomPage findPosts(Integer page, Integer size, String sort, PostType postType,
                                    PostStatus postStatus, DisabilityType disabilityType, AssistanceType assistanceType, Long id) {
        List<PostResDto> postResDtos = queryFactory
                .selectFrom(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType),
                        buildPostDisabilityTypeExpression(disabilityType), buildPostAssistanceTypeExpression(assistanceType),
                        buildAuthorIdExpression(id))
                .offset((long) page * size)
                .limit(size)
                .orderBy(buildOrderSpecifier(sort))
                .fetch()
                .stream()
                .map(PostResDto::new)
                .toList();

        Long totalElements = queryFactory
                .select(post.count())
                .from(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType))
                .fetchOne();

        // content, totalElements, last
        return new PostCustomPage(postResDtos, totalElements, postResDtos.size() < size);
    }

    private BooleanExpression buildAuthorIdExpression(Long id) {
        return id == null ? null : post.author.id.eq(id);
    }

    private BooleanExpression buildPostTypeExpression(PostType postType) {
        return postType == null ? null : post.postType.eq(postType);
    }

    private BooleanExpression buildPostStatusExpression(PostStatus postStatus) {
        return postStatus == null ? null : post.postStatus.eq(postStatus);
    }

    // 없음, 시각장애, 청각장애, 지적장애, 지체장애, 자폐성장애, 뇌병변장애, 정신장애
    private BooleanExpression buildPostDisabilityTypeExpression(DisabilityType disabilityType) {
//        return disabilityType == null ? null : post.disabilityType.eq(disabilityType);
        return disabilityType == null ? null : post.author.disabilityType.eq(disabilityType);
    }

    // 광주광역시, 남구, 북구, 서구, 동구, 광산구
    private BooleanExpression buildPostDistrictExpression(District district) {
        return district == null ? null : post.district.eq(district);
    }

    // 교육, 생활
    private BooleanExpression buildPostAssistanceTypeExpression(AssistanceType assistanceType) {
        return assistanceType == null ? null : post.assistanceType.eq(assistanceType);
    }

    private OrderSpecifier<?> buildOrderSpecifier(String sort) {
        return switch (sort.toLowerCase()) {
            case "desc" -> post.createdAt.desc();
            case "asc" -> post.createdAt.asc();
            default -> throw new IllegalArgumentException("올바르지 않은 정렬 방식입니다.");
        };
    }
}
