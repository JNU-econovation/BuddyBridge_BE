package econo.buddybridge.post.event;

import econo.buddybridge.common.event.DomainEvent;
import econo.buddybridge.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDeleteEvent extends DomainEvent {

    private final Post post;

    public static PostDeleteEvent from(Post post) {
        return new PostDeleteEvent(post);
    }
}
