package econo.buddybridge.chat.chatmessage.entity;

import lombok.Getter;

@Getter
public enum MessageType {
    JOIN("JOIN"),
    CHAT("CHAT"),
    LEAVE("LEAVE"),
    DELETE("DELETE");

    private final String messageType;

    private MessageType(String messageType){
        this.messageType = messageType;
    }

}
