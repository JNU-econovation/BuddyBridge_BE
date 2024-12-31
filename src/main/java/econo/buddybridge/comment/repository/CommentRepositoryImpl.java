package econo.buddybridge.comment.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.comment.dto.CommentCustomPage;
import econo.buddybridge.comment.dto.CommentResDto;
import econo.buddybridge.comment.dto.MyPageCommentCustomPage;
import econo.buddybridge.comment.dto.MyPageCommentResDto;
import econo.buddybridge.comment.dto.QAuthorDto;
import econo.buddybridge.comment.dto.QCommentResDto;
import econo.buddybridge.comment.dto.QMyPageCommentResDto;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.repository.PostRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static econo.buddybridge.comment.entity.QComment.comment;
import static econo.buddybridge.post.entity.QPost.post;
import static org.springframework.data.domain.Sort.Order;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final PostRepositoryImpl postRepositoryImpl;

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

        List<MyPageCommentResDto> content = queryFactory
                .select(new QMyPageCommentResDto( // MyPageCommentResDto를 생성
                        comment.content,
                        comment.id,
                        comment.post.id,
                        comment.post.title,
                        Expressions.constant(PostStatus.RECRUITING),
                        comment.post.postType,
                        comment.post.disabilityType,
                        comment.post.assistanceType,
                        comment.createdAt
                ))
                .from(comment) // comment를 기준으로 조회
                .where(
                        buildPostTypeExpression(postType),
                        comment.author.id.eq(memberId)
                )
                .orderBy(comment.createdAt.desc())
                .limit(size)
                .offset((long) page * size)
                .fetch();

        // 해당하는 게시글 ID 리스트 조회
        List<Long> postIds = content.stream().map(MyPageCommentResDto::postId).toList();

        // 각 게시글 ID에 대해 전체 매칭 조회
        Map<Long, List<Matching>> postMatchings = postRepositoryImpl.getMatchings(postIds);

        // content를 순환하면서 각 게시글의 해당하는 postStatus 설정
        List<MyPageCommentResDto> updatedContent = updatePostStatusInContent(content, postMatchings);

        // 댓글 단 게시글 수 조회
        Long totalElements = queryFactory
                .select(comment.id.count())
                .from(comment)
                .where(
                        buildPostTypeExpression(postType),
                        comment.author.id.eq(memberId)
                )
                .fetchOne();

        return new MyPageCommentCustomPage(updatedContent, totalElements, content.size() < size);
    }

    private List<MyPageCommentResDto> updatePostStatusInContent(List<MyPageCommentResDto> content, Map<Long, List<Matching>> postMatchings) {
        return content.stream()
                .map(comment -> {
                    List<Matching> matchings = postMatchings.getOrDefault(comment.postId(), Collections.emptyList());
                    PostStatus status = postRepositoryImpl.calculatePostStatus(matchings);
                    return MyPageCommentResDto.builder()
                            .content(comment.content())
                            .commentId(comment.commentId())
                            .postId(comment.postId())
                            .postTitle(comment.postTitle())
                            .postStatus(status)
                            .postType(comment.postType())
                            .disabilityType(comment.disabilityType())
                            .assistanceType(comment.assistanceType())
                            .postCreatedAt(comment.postCreatedAt())
                            .build();
                }).toList();
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
