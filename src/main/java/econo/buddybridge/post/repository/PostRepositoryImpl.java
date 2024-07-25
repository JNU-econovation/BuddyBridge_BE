package econo.buddybridge.post.repository;

import static econo.buddybridge.post.entity.QPost.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PostCustomPage findPosts(Integer page, Integer size, String sort, PostType postType, PostStatus postStatus) {
        List<PostResDto> postResDtos = queryFactory
                .selectFrom(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType))
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

    private BooleanExpression buildPostTypeExpression(PostType postType) {
        return postType == null ? null : post.postType.eq(postType);
    }

    private BooleanExpression buildPostStatusExpression(PostStatus postStatus) {
        return postStatus == null ? null : post.postStatus.eq(postStatus);
    }

    private OrderSpecifier<?> buildOrderSpecifier(String sort) {
        return switch (sort.toLowerCase()) {
            case "desc" -> post.createdAt.desc();
            case "asc" -> post.createdAt.asc();
            default -> throw new IllegalArgumentException("올바르지 않은 정렬 방식입니다.");
        };
    }
}
