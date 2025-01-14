package econo.buddybridge.chat.chatmessage.entity;

import lombok.Getter;

@Getter
public enum MessageType {
    INFO("INFO"), // JOIN, LEAVE, ETC...
    CHAT("CHAT"),
    DELETE("DELETE"),
    REQUEST("REQUEST"),
    ERROR("ERROR");

    private final String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

}
