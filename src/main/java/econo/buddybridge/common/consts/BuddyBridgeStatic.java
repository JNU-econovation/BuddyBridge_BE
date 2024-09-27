package econo.buddybridge.common.consts;

import econo.buddybridge.post.entity.PostType;

public class BuddyBridgeStatic {

    public static final String CHAT_ROOM_PREFIX = "/chat/";
    public static final String POST_TAKER_PREFIX = "/help-me/";
    public static final String POST_GIVER_PREFIX = "/help-you/";

    public static final String CHAT_NOTIFICATION_MESSAGE = "%s님이 메시지를 보냈습니다. - %s";
    public static final String COMMENT_NOTIFICATION_MESSAGE = "%s님이 댓글을 남겼습니다. - %s";

    public static final String CHAT_NOTIFICATION_URL = CHAT_ROOM_PREFIX + "%d";
    public static final String COMMENT_NOTIFICATION_URL_TAKER = POST_TAKER_PREFIX + "%d";
    public static final String COMMENT_NOTIFICATION_URL_GIVER = POST_GIVER_PREFIX + "%d";

    public static String getCommentNotificationUrl(PostType postType, Long postId) {
        return PostType.TAKER.equals(postType)
                ? String.format(COMMENT_NOTIFICATION_URL_TAKER, postId)
                : String.format(COMMENT_NOTIFICATION_URL_GIVER, postId);
    }
}
