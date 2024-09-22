package econo.buddybridge.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.*;
import econo.buddybridge.post.exception.PostInvalidSortValueException;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static econo.buddybridge.post.entity.QPost.post;
import static econo.buddybridge.post.entity.QPostLike.postLike;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PostCustomPage findPosts(Long memberId, Integer page, Integer size, String sort, PostType postType,
                                    PostStatus postStatus, DisabilityType disabilityType, AssistanceType assistanceType) {

        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType),
                        buildPostDisabilityTypeExpression(disabilityType), buildPostAssistanceTypeExpression(assistanceType))
                .offset((long) page * size)
                .limit(size)
                .orderBy(buildOrderSpecifier(sort))
                .fetch();

        List<PostResDto> content;
        if (memberId != null) {
            List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
            Set<Long> postLikedIds = new HashSet<>(
                    queryFactory
                            .select(postLike.post.id)
                            .from(postLike)
                            .where(postLike.member.id.eq(memberId), postLike.post.id.in(postIds))
                            .fetch()
            );

            content = posts.stream()
                    .map(post -> new PostResDto(post, postLikedIds.contains(post.getId())))
                    .collect(Collectors.toList());
        } else {
            content = posts.stream()
                    .map(PostResDto::new)
                    .collect(Collectors.toList());
        }

        Long totalElements = queryFactory
                .select(post.count())
                .from(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType),
                        buildPostDisabilityTypeExpression(disabilityType), buildPostAssistanceTypeExpression(assistanceType))
                .fetchOne();

        // content, totalElements, last
        return new PostCustomPage(content, totalElements, content.size() < size);
    }

    @Override
    public PostCustomPage findPostsMyPage(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        List<PostResDto> content = queryFactory
                .selectFrom(post)
                .where(buildMemberIdExpression(memberId), buildPostTypeExpression(postType))
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
                .where(buildMemberIdExpression(memberId), buildPostTypeExpression(postType))
                .fetchOne();

        return new PostCustomPage(content, totalElements, content.size() < size);
    }

    private BooleanExpression buildMemberIdExpression(Long memberId) {
        return memberId == null ? null : post.author.id.eq(memberId);
    }

    private BooleanExpression buildPostTypeExpression(PostType postType) {
        return postType == null ? null : post.postType.eq(postType);
    }

    private BooleanExpression buildPostStatusExpression(PostStatus postStatus) {
        return postStatus == null ? null : post.postStatus.eq(postStatus);
    }

    // 없음, 시각장애, 청각장애, 지적장애, 지체장애, 자폐성장애, 뇌병변장애, 정신장애
    private BooleanExpression buildPostDisabilityTypeExpression(DisabilityType disabilityType) {
        return disabilityType == null ? null : post.disabilityType.eq(disabilityType);
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
            default -> throw PostInvalidSortValueException.EXCEPTION;
        };
    }
}
