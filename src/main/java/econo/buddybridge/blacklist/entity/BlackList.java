package econo.buddybridge.blacklist.entity;

import econo.buddybridge.common.persistence.BaseEntity;
import econo.buddybridge.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "BLACK_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlackList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Member reportedMember;

    @Builder
    public BlackList(Member reportedMember) {
        this.reportedMember = reportedMember;
    }
}
