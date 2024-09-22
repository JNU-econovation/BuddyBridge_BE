package econo.buddybridge.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.PostLike;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.exception.PostInvalidSortValueException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static econo.buddybridge.post.entity.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId) {

        PostLike like = queryFactory.selectFrom(postLike)
                .where(postLike.post.id.eq(postId).and(postLike.member.id.eq(memberId)))
                .fetchOne();

        return Optional.ofNullable(like);
    }

    @Override
    public PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        List<PostResDto> content = queryFactory
                .select(postLike.post)
                .from(postLike)
                .where(postLike.member.id.eq(memberId), buildPostTypeExpression(postType))
                .offset((long) page * size)
                .orderBy(buildOrderSpecifier(sort))
                .fetch()
                .stream()
                .map(post -> new PostResDto(post,true))
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
            default -> throw PostInvalidSortValueException.EXCEPTION;
        };
    }

}
