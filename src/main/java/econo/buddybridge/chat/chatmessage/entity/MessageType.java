package econo.buddybridge.chat.chatmessage.entity;

import lombok.Getter;

@Getter
public enum MessageType {
    INFO("INFO"), // JOIN, LEAVE, ETC...
    CHAT("CHAT"),
    DELETE("DELETE");

    private final String messageType;

    private MessageType(String messageType){
        this.messageType = messageType;
    }

}
