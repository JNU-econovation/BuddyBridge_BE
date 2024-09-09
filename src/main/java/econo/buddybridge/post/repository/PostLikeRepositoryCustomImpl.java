package econo.buddybridge.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.post.entity.PostLike;
import lombok.RequiredArgsConstructor;

import static econo.buddybridge.post.entity.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public PostLike findByPostIdAndMemberId(Long postId, Long memberId) {

        return queryFactory.selectFrom(postLike)
                .where(postLike.post.id.eq(postId).and(postLike.member.id.eq(memberId)))
                .fetchOne();
    }

}
