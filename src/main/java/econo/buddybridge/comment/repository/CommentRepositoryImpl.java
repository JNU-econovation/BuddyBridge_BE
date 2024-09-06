package econo.buddybridge.comment.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.comment.dto.*;
import econo.buddybridge.comment.entity.QComment;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static econo.buddybridge.comment.entity.QComment.comment;
import static econo.buddybridge.post.entity.QPost.post;
import static org.springframework.data.domain.Sort.Order;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public CommentCustomPage findByPost(Post post, Long cursor, Pageable page) {
        Order order = getPageOrder(page);
        int pageSize = page.getPageSize();

        List<CommentResDto> content = queryFactory
                .select(new QCommentResDto(
                        comment.id,
                        comment.post.id,
                        new QAuthorDto(
                                comment.author.id,
                                comment.author.nickname,
                                comment.author.profileImageUrl,
                                comment.author.gender,
                                comment.author.age
                        ),
                        comment.content,
                        comment.createdAt,
                        comment.modifiedAt
                ))
                .from(comment)
                .where(comment.post.eq(post), buildCursorPredicate(cursor, order))
                .orderBy(createOrderSpecifier(order))
                .limit(pageSize + 1)
                .fetch();

        Boolean nextPage = false;
        if (content.size() > pageSize) {
            content.removeLast();
            nextPage = true;
        }

        Long nextCursor = nextPage ? content.getLast().commentId() : -1L;

        return new CommentCustomPage(content, nextCursor, nextPage);
    }

    @Override
    public MyPageCommentCustomPage findByMemberId(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        QComment subComment = new QComment("subComment");

        List<MyPageCommentResDto> content = queryFactory
                .select(new QMyPageCommentResDto( // MyPageCommentResDto를 생성
                        comment.content,
                        comment.id,
                        comment.post.id,
                        comment.post.title,
                        comment.post.postStatus,
                        comment.post.postType,
                        comment.post.disabilityType,
                        comment.post.assistanceType,
                        comment.createdAt
                ))
                .from(comment) // comment를 기준으로 조회
                .where(
                        buildPostTypeExpression(postType),
                        comment.author.id.eq(memberId),
                        comment.createdAt.eq(
                                JPAExpressions
                                        .select(subComment.createdAt.max()) // 댓글의 생성일 중 가장 최근 것을
                                        .from(subComment) // subComment를 기준으로 조회
                                        .where(subComment.post.id.eq(comment.post.id), // subComment의 post가 comment의 post와 같고
                                                subComment.author.id.eq(memberId)) // subComment의 author가 comment의 author와 같은 것을
                        )
                )
                .orderBy(comment.createdAt.desc())
                .limit(size)
                .offset((long) page * size)
                .fetch();

        // 댓글 단 게시글 수 조회
        Long totalElements = queryFactory
                .select(comment.post.id.countDistinct())
                .from(comment)
                .where(
                        buildPostTypeExpression(postType),
                        comment.author.id.eq(memberId)
                )
                .fetchOne();

        return new MyPageCommentCustomPage(content, totalElements, content.size() < size);
    }

    private BooleanExpression buildPostTypeExpression(PostType postType) {
        return postType == null ? null : post.postType.eq(postType);
    }

    private Order getPageOrder(Pageable page) {
        return page.getSort().get().findFirst().orElseThrow(() -> new IllegalArgumentException("정렬 조건을 가져야 합니다."));
    }

    private Predicate buildCursorPredicate(Long cursor, Order order) {
        if (cursor == null) {
            return null;
        }
        return order.isAscending() ? comment.id.gt(cursor) : comment.id.lt(cursor);
    }

    private OrderSpecifier<?> createOrderSpecifier(Order order) {
        return order.isAscending() ? comment.id.asc() : comment.id.desc();
    }
}
