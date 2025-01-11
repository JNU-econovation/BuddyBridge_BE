package econo.buddybridge.comment.event;

import econo.buddybridge.comment.entity.Comment;
import econo.buddybridge.common.event.DomainEvent;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDeleteEvent extends DomainEvent {

    private final List<Comment> comments;

    public static CommentDeleteEvent from(List<Comment> comments) {
        return new CommentDeleteEvent(comments);
    }
}
