package econo.buddybridge.post.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.entity.QPost;
import econo.buddybridge.post.exception.PostInvalidSortValueException;
import econo.buddybridge.post.exception.PostNotFoundException;
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
    private final MatchingRepository matchingRepository;

    @Override // 단일 게시글 조회
    public PostResDto findByMemberIdAndPostId(Long memberId, Long postId) {
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

        return new PostResDto(content, isLiked, getMatchingDoneCount(postId));
    }

    @Override // 게시글 목록 조회
    public PostCustomPage findPosts(Long memberId, Integer page, Integer size, String sort, PostType postType,
                                    PostStatus postStatus, List<DisabilityType> disabilityType, List<AssistanceType> assistanceType) {

        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType, post),
                        buildPostDisabilityTypesExpression(disabilityType), buildPostAssistanceTypesExpression(assistanceType))
                .offset((long) page * size)
                .limit(size)
                .orderBy(buildOrderSpecifier(sort, post))
                .fetch();

        List<PostResDto> content = getPostResDtos(memberId, posts);

        Long totalElements = queryFactory
                .select(post.count())
                .from(post)
                .where(buildPostStatusExpression(postStatus), buildPostTypeExpression(postType, post),
                        buildPostDisabilityTypesExpression(disabilityType), buildPostAssistanceTypesExpression(assistanceType))
                .fetchOne();

        return new PostCustomPage(content, totalElements, content.size() < size);
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

        List<PostResDto> content = getPostResDtos(memberId, posts);

        Long totalElements = queryFactory
                .select(post.count())
                .from(post)
                .where(buildMemberIdExpression(memberId), buildPostTypeExpression(postType, post))
                .fetchOne();

        return new PostCustomPage(content, totalElements, content.size() < size);
    }

    @Override // 내가 좋아요한 게시글 목록 조회
    public PostCustomPage findPostsByLikes(Long memberId, Integer page, Integer size, String sort, PostType postType) {

        List<PostResDto> content = queryFactory
                .select(postLike.post)
                .from(postLike)
                .where(postLike.member.id.eq(memberId), buildPostTypeExpression(postType, postLike.post))
                .offset((long) page * size)
                .orderBy(buildOrderSpecifier(sort, postLike.post))
                .fetch()
                .stream()
                .map(post -> new PostResDto(post, true, getMatchingDoneCount(post.getId())))
                .toList();

        Long totalElements = queryFactory
                .select(postLike.count())
                .from(postLike)
                .where(postLike.member.id.eq(memberId))
                .fetchOne();

        return new PostCustomPage(content, totalElements, content.size() < size);
    }

    private List<PostResDto> getPostResDtos(Long memberId, List<Post> posts) {
        if (memberId != null) {
            List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
            Set<Long> postLikedIds = new HashSet<>(
                    queryFactory
                            .select(postLike.post.id)
                            .from(postLike)
                            .where(postLike.member.id.eq(memberId), postLike.post.id.in(postIds))
                            .fetch()
            );

            return posts.stream()
                    .map(post -> new PostResDto(post, postLikedIds.contains(post.getId()), getMatchingDoneCount(post.getId())))
                    .toList();
        }

        return posts.stream()
                .map(post -> new PostResDto(post, false, getMatchingDoneCount(post.getId())))
                .toList();
    }

    private Integer getMatchingDoneCount(Long postId) {
        return matchingRepository.countMatchingDoneByPostId(postId);
    }

    private BooleanExpression buildMemberIdExpression(Long memberId) {
        return memberId == null ? null : post.author.id.eq(memberId);
    }

    private BooleanExpression buildPostTypeExpression(PostType postType, QPost qPost) {
        return postType == null ? null :  qPost.postType.eq(postType);
    }

    private BooleanExpression buildPostStatusExpression(PostStatus postStatus) {
        return postStatus == null ? null : post.postStatus.eq(postStatus);
    }

    // 없음, 시각장애, 청각장애, 지적장애, 지체장애, 자폐성장애, 뇌병변장애, 정신장애
    private BooleanExpression buildPostDisabilityTypesExpression(List<DisabilityType> disabilityTypes) {
        if (disabilityTypes == null || disabilityTypes.isEmpty()) {
            return null;
        }
        return post.disabilityType.in(disabilityTypes);
    }

    // 광주광역시, 남구, 북구, 서구, 동구, 광산구
    private BooleanExpression buildPostDistrictExpression(District district) {
        return district == null ? null : post.district.eq(district);
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
