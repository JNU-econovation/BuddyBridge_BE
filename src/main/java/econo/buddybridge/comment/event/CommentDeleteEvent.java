package econo.buddybridge.comment.event;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.common.event.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDeleteEvent extends DomainEvent {

    private final Comment comment;

    public static CommentDeleteEvent from(Comment comment) {
        return new CommentDeleteEvent(comment);
    }
}
