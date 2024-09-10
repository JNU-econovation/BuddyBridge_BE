package econo.buddybridge.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.entity.PostType;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static econo.buddybridge.post.entity.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PostLike findByPostIdAndMemberId(Long postId, Long memberId) {

        return queryFactory.selectFrom(postLike)
                .where(postLike.post.id.eq(postId).and(postLike.member.id.eq(memberId)))
                .fetchOne();
    }

    @Override
    public PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        // Todo: 쿼리 최적화 고려
        List<PostResDto> content = queryFactory
                .select(postLike.post)
                .from(postLike)
                .where(postLike.member.id.eq(memberId), buildPostTypeExpression(postType))
                .offset((long) page * size)
                .orderBy(buildOrderSpecifier(sort))
                .fetch()
                .stream()
                .map(PostResDto::new)
                .toList();

        Long totalElements = queryFactory
                .select(postLike.count())
                .from(postLike)
                .where(postLike.member.id.eq(memberId))
                .fetchOne();

        return new PostCustomPage(content, totalElements, content.size() < size);
    }


    private BooleanExpression buildPostTypeExpression(PostType postType) {
        return postType != null ? postLike.post.postType.eq(postType) : null;
    }

    private OrderSpecifier<?> buildOrderSpecifier(String sort) {
        return switch (sort.toLowerCase()) {
            case "desc" -> postLike.post.createdAt.desc();
            case "asc" -> postLike.post.createdAt.asc();
            default -> throw new IllegalArgumentException("Unexpected value: " + sort);
        };
    }

}
