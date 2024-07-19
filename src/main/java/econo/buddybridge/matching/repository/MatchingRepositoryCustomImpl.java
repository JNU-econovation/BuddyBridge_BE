package econo.buddybridge.matching.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import econo.buddybridge.chat.chatmessage.entity.QChatMessage;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.QMatching;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryCustomImpl implements MatchingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Matching> findMatchingByTakerIdOrGiverId(Long memberId, Pageable pageable) {
        return findMatchingByTakerIdOrGiverIdAndCursor(memberId, null, pageable);
    }

    @Override
    public Slice<Matching> findMatchingByTakerIdOrGiverIdAndIdLessThan(Long memberId, LocalDateTime cursor, Pageable pageable) {
        return findMatchingByTakerIdOrGiverIdAndCursor(memberId, cursor, pageable);
    }

    private Slice<Matching> findMatchingByTakerIdOrGiverIdAndCursor(Long memberId,LocalDateTime cursor, Pageable pageable) {
        QMatching matching = QMatching.matching;
        QChatMessage chatMessage = QChatMessage.chatMessage;

        List<Matching> matchingList = queryFactory.selectFrom(matching)
                .where(matching.taker.id.eq(memberId).or(matching.giver.id.eq(memberId)))
                .fetch();

        // 각 Matching에 대해 마지막 ChatMessage의 생성 시간
        Map<Long, LocalDateTime> latestMessageTimeMap = matchingList.stream()
                .collect(Collectors.toMap(
                        Matching::getId,
                        m -> {
                            LocalDateTime latestMessageTime = queryFactory.select(chatMessage.createdAt.max())
                                    .from(chatMessage)
                                    .where(chatMessage.matching.id.eq(m.getId()))
                                    .fetchOne();
                            return latestMessageTime != null ? latestMessageTime : LocalDateTime.MIN;
                        }
                ));

        // Matching 리스트를 마지막 메시지의 createdAt을 기준으로 정렬
        List<Matching> sortedMatchingList = matchingList.stream()
                .filter(m -> cursor == null || latestMessageTimeMap.get(m.getId()).isAfter(cursor))
                .sorted((m1, m2) -> {
                    LocalDateTime time1 = latestMessageTimeMap.get(m1.getId());
                    LocalDateTime time2 = latestMessageTimeMap.get(m2.getId());
                    return time2.compareTo(time1); // 내림차순 정렬
                })
                .skip(pageable.getOffset()) // page 기반 시 필요
                .limit(pageable.getPageSize() + 1)
                .toList();

        return createSlice(sortedMatchingList, pageable);
    }

    private Slice<Matching> createSlice(List<Matching> matchingList, Pageable pageable){
        boolean hasNext = matchingList.size() > pageable.getPageSize();
        if(hasNext){
            matchingList.remove(matchingList.size() - 1);
        }
        return new SliceImpl<>(matchingList,pageable,hasNext);
    }
}
