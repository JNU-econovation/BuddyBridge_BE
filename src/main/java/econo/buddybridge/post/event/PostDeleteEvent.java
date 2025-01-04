package econo.buddybridge.post.event;

import econo.buddybridge.common.event.DomainEvent;
import econo.buddybridge.post.entity.Post;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDeleteEvent extends DomainEvent {

    private final List<Post> posts;

    public static PostDeleteEvent from(List<Post> posts) {
        return new PostDeleteEvent(posts);
    }
}
