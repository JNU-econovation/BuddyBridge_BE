package econo.buddybridge.matching.entity;

import econo.buddybridge.certification.exception.VolunteerCertificationAllowedOnlyVolunteeringCompletedException;
import econo.buddybridge.certification.exception.VolunteerCertificationVolunteererMismatchException;
import econo.buddybridge.chat.chatmessage.entity.ChatMessage;
import econo.buddybridge.common.persistence.SoftDeletableEntity;
import econo.buddybridge.matching.exception.MatchingNotParticipantException;
import econo.buddybridge.matching.exception.MatchingStatusNotDoneException;
import econo.buddybridge.matching.state.MatchingState;
import econo.buddybridge.matching.state.MatchingStatusChangeEvent;
import econo.buddybridge.matching.state.impl.DoneState;
import econo.buddybridge.matching.state.impl.FailedState;
import econo.buddybridge.matching.state.impl.PendingState;
import econo.buddybridge.matching.state.impl.VolunteeringCompletedState;
import econo.buddybridge.matching.state.impl.VolunteeringVerifiedState;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MATCHING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taker_id")
    private Member taker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id")
    private Member giver;

    // 매칭 상태
    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @Transient
    private MatchingState matchingState;

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToOne(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private CertificationTracking certificationTracking;

    @Builder
    public Matching(Post post, Member taker, Member giver, MatchingStatus matchingStatus) {
        this.post = post;
        this.taker = taker;
        this.giver = giver;
        this.matchingStatus = matchingStatus;
        initializeMatchingState();
    }

    public boolean canRequestCertification() {
        return certificationTracking.isRequestedWithinOneDay();
    }

    public void handleEvent(MatchingStatusChangeEvent event, MemberRole role) {
        MatchingState newState = matchingState.handleEvent(event, role);
        this.matchingState = newState;
        this.matchingStatus = newState.getStatus();
    }

    public void initializeMatchingState() {
        switch (matchingStatus) {
            case PENDING -> this.matchingState = PendingState.getInstance();
            case DONE -> this.matchingState = DoneState.getInstance();
            case FAILED -> this.matchingState = FailedState.getInstance();
            case VOLUNTEERING_COMPLETED -> this.matchingState = VolunteeringCompletedState.getInstance();
            case VOLUNTEERING_VERIFIED -> this.matchingState = VolunteeringVerifiedState.getInstance();
        }
    }

    public void validateParticipants(Member member) {
        if (!taker.equals(member) && !giver.equals(member)) {
            throw MatchingNotParticipantException.EXCEPTION;
        }
    }

    public void validateMatchingStatusDone() {
        if (!this.matchingStatus.equals(MatchingStatus.DONE)) {
            throw MatchingStatusNotDoneException.EXCEPTION;
        }
    }

    public void validateCreateVolunteerer(Member volunteerer) {
        validateVolunteerer(volunteerer);
        validateMatchingStatusVolunteeringCompleted();
    }

    public void validateUpdateVolunteerer(Member volunteerer) {
        validateVolunteerer(volunteerer);
    }

    public void validateVolunteerer(Member volunteerer) {
        if (!this.giver.equals(volunteerer)) {
            throw VolunteerCertificationVolunteererMismatchException.EXCEPTION;
        }
    }

    private void validateMatchingStatusVolunteeringCompleted() {
        if (!this.matchingStatus.equals(MatchingStatus.VOLUNTEERING_COMPLETED)) {
            throw VolunteerCertificationAllowedOnlyVolunteeringCompletedException.EXCEPTION;
        }
    }

    @PostLoad
    private void postLoad() {
        initializeMatchingState();
    }
}
