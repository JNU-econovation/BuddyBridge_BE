package econo.buddybridge.comment.repository;

import static econo.buddybridge.comment.entity.QComment.comment;
import static org.springframework.data.domain.Sort.Order;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentResDto;
import econo.buddybridge.comment.dto.QAuthorDto;
import econo.buddybridge.comment.dto.QCommentResDto;
import econo.buddybridge.post.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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
                                comment.author.profileImageUrl
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

        Boolean isLast = true;
        if (content.size() > pageSize) {
            content.removeLast();
            isLast = false;
        }

        Long nextCursor = isLast ? -1L : content.getLast().commentId();

        return new CommentCustomPage(content, nextCursor, isLast);
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
