package econo.buddybridge.post.entity;

import lombok.Getter;

@Getter
public enum PostType {
    TAKER("도와줄래요?"),
    GIVER("도와줄게요!");

    private final String postType;

    PostType(String postType) {
        this.postType = postType;
    }
}
