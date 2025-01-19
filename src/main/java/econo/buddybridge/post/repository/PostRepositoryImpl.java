package econo.buddybridge.post.repository;

import static econo.buddybridge.matching.entity.QMatching.matching;
import static econo.buddybridge.post.entity.QPost.post;
import static econo.buddybridge.post.entity.QPostLike.postLike;
import static econo.buddybridge.post.mapper.PostMapper.toPostDetailDto;
import static econo.buddybridge.post.mapper.PostMapper.toPostListItemDto;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.post.dto.PostListItemDto;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.QPost;
import econo.buddybridge.post.exception.PostInvalidSortValueException;
import econo.buddybridge.post.exception.PostNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override // 단일 게시글 조회
    public PostDetailDto findByMemberIdAndPostId(Long memberId, Long postId) {
        Post content = queryFactory
                .selectFrom(post)
                .where(post.id.eq(postId))
                .fetchOne();

        if (content == null) {
            throw PostNotFoundException.EXCEPTION;
        }

        Boolean isLiked = memberId != null && queryFactory
                .select(postLike.post.id)
                .from(postLike)
                .where(postLike.member.id.eq(memberId), postLike.post.id.eq(postId))
                .fetchOne() != null;

        List<Matching> matchings = queryFactory.
                selectFrom(matching)
                .where(matching.post.id.eq(postId))
                .fetch();

        PostStatus postStatus = calculatePostStatus(matchings);

        return toPostDetailDto(content, isLiked, postStatus);
    }

    @Override // 신고된 단일 게시글 조회
    public PostDetailDto findByMemberIdAndReportedPostId(Long postId) {
        Post content = queryFactory
                .selectFrom(post)
                .where(post.id.eq(postId))
                .fetchOne();

        if (content == null) {
            throw PostNotFoundException.EXCEPTION;
        }

        List<Matching> matchings = queryFactory.
                selectFrom(matching)
                .where(matching.post.id.eq(postId))
                .fetch();

        PostStatus postStatus = calculatePostStatus(matchings);

        return toPostDetailDto(content, false, postStatus);
    }

    @Override // 게시글 목록 조회
    public PostCustomPage findPosts(Long memberId, Integer page, Integer size, String sort, PostType postType,
            PostStatus postStatus, List<DisabilityType> disabilityType, List<AssistanceType> assistanceType) {

        List<Long> finishedPostIds = Collections.emptyList();

        if (postStatus != null) {
            finishedPostIds = queryFactory
                    .select(matching.post.id)
                    .from(matching)
                    .where(
                            matching.matchingStatus.in(
                                    MatchingStatus.DONE,
                                    MatchingStatus.VOLUNTEERING_COMPLETED,
                                    MatchingStatus.VOLUNTEERING_VERIFIED
                            )
                    )
                    .fetch();
        }

        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(buildPostTypeExpression(postType, post), buildPostStatusExpression(finishedPostIds, postStatus),
                        buildPostDisabilityTypesExpression(disabilityType), buildPostAssistanceTypesExpression(assistanceType))
                .offset((long) page * size)
                .limit(size)
                .orderBy(buildOrderSpecifier(sort, post))
                .fetch();

        List<PostListItemDto> content = getContent(memberId, posts, false);

        Long totalElements = queryFactory
                .select(post.count())
                .from(post)
                .where(buildPostTypeExpression(postType, post), buildPostStatusExpression(finishedPostIds, postStatus),
                        buildPostDisabilityTypesExpression(disabilityType), buildPostAssistanceTypesExpression(assistanceType))
                .fetchOne();

        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        return new PostCustomPage(content, totalElements, last);
    }

    @Override // 내가 작성한 게시글 목록 조회 - 마이페이지
    public PostCustomPage findPostsMyPage(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(buildMemberIdExpression(memberId), buildPostTypeExpression(postType, post))
                .offset((long) page * size)
                .limit(size)
                .orderBy(buildOrderSpecifier(sort, post))
                .fetch();

        List<PostListItemDto> content = getContent(memberId, posts, false);

        Long totalElements = queryFactory
                .select(post.count())
                .from(post)
                .where(buildMemberIdExpression(memberId), buildPostTypeExpression(postType, post))
                .fetchOne();

        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        return new PostCustomPage(content, totalElements, last);
    }

    @Override // 내가 좋아요한 게시글 목록 조회
    public PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        List<Post> posts = queryFactory
                .select(postLike.post)
                .from(postLike)
                .where(postLike.member.id.eq(memberId), buildPostTypeExpression(postType, postLike.post))
                .offset((long) page * size)
                .orderBy(buildOrderSpecifier(sort, postLike.post))
                .fetch();

        List<PostListItemDto> content = getContent(memberId, posts, true);

        Long totalElements = queryFactory
                .select(postLike.count())
                .from(postLike)
                .where(postLike.member.id.eq(memberId))
                .fetchOne();

        long totalPage = (totalElements + size - 1) / size;
        boolean last = page >= totalPage - 1;

        return new PostCustomPage(content, totalElements, last);
    }

    private List<PostListItemDto> getContent(Long memberId, List<Post> posts, Boolean isLikedPage) {
        List<Long> postIds = getPostIds(posts);
        Map<Long, List<Matching>> matchings = getMatchings(postIds);
        return getPostResDtos(memberId, posts, matchings, isLikedPage);
    }

    private List<PostListItemDto> getPostResDtos(Long memberId, List<Post> posts, Map<Long, List<Matching>> matchings, Boolean isLikedPage) {

        if (memberId != null && isLikedPage) {
            return posts.stream()
                    .map(post -> {
                        List<Matching> postMatchings = matchings.getOrDefault(post.getId(), Collections.emptyList());
                        PostStatus status = calculatePostStatus(postMatchings);
                        return toPostListItemDto(post, true, status);
                    })
                    .toList();
        }

        Map<Long, Boolean> tempPostLikeRepository = new HashMap<>();
        if (memberId != null) {
            List<Long> postIds = getPostIds(posts);
            Set<Long> postLikedIds = new HashSet<>(
                    queryFactory
                            .select(postLike.post.id)
                            .from(postLike)
                            .where(postLike.member.id.eq(memberId), postLike.post.id.in(postIds))
                            .fetch()
            );
            posts.forEach(post -> tempPostLikeRepository.put(post.getId(), postLikedIds.contains(post.getId())));
        }

        return posts.stream()
                .map(post -> {
                    List<Matching> postMatchings = matchings.getOrDefault(post.getId(), Collections.emptyList());
                    PostStatus status = calculatePostStatus(postMatchings);
                    boolean isLiked = tempPostLikeRepository.getOrDefault(post.getId(), false);
                    return toPostListItemDto(post, isLiked, status);
                })
                .toList();
    }

    public PostStatus calculatePostStatus(List<Matching> matchings) {
        List<MatchingStatus> matchingStatuses = MatchingStatus.getCompletedStatuses();

        return matchings
                .stream()
                .anyMatch(m -> matchingStatuses.contains(m.getMatchingStatus()))
                ? PostStatus.FINISHED
                : PostStatus.RECRUITING;
    }

    public Map<Long, List<Matching>> getMatchings(List<Long> postIds) {
        return queryFactory
                .selectFrom(matching)
                .where(matching.post.id.in(postIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(m -> m.getPost().getId()));
    }

    private List<Long> getPostIds(List<Post> posts) {
        return posts.stream().map(Post::getId).toList();
    }

    private BooleanExpression buildPostStatusExpression(List<Long> finishedPostIds, PostStatus postStatus) {

        if (postStatus == null) {
            return null;
        }

        return switch (postStatus) {
            case RECRUITING -> post.id.notIn(finishedPostIds);
            case FINISHED -> post.id.in(finishedPostIds);
        };
    }

    private BooleanExpression buildMemberIdExpression(Long memberId) {
        return memberId == null ? null : post.author.id.eq(memberId);
    }

    private BooleanExpression buildPostTypeExpression(PostType postType, QPost qPost) {
        return postType == null ? null : qPost.postType.eq(postType);
    }

    // 없음, 시각장애, 청각장애, 지적장애, 지체장애, 자폐성장애, 뇌병변장애, 정신장애
    private BooleanExpression buildPostDisabilityTypesExpression(List<DisabilityType> disabilityTypes) {
        if (disabilityTypes == null || disabilityTypes.isEmpty()) {
            return null;
        }
        return post.disabilityType.in(disabilityTypes);
    }

    // 학습, 식사, 이동
    private BooleanExpression buildPostAssistanceTypesExpression(List<AssistanceType> assistanceTypes) {
        if (assistanceTypes == null || assistanceTypes.isEmpty()) {
            return null;
        }
        return post.assistanceType.in(assistanceTypes);
    }

    private OrderSpecifier<?> buildOrderSpecifier(String sort, QPost post) {
        return switch (sort.toLowerCase()) {
            case "desc" -> post.createdAt.desc();
            case "asc" -> post.createdAt.asc();
            default -> throw PostInvalidSortValueException.EXCEPTION;
        };
    }
}
