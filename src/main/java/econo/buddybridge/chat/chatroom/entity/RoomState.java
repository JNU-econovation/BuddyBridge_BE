package econo.buddybridge.chat.chatroom.entity;

import lombok.Getter;

@Getter
public enum RoomState {
    ACCEPT("ACCEPT"),
    REJECT("REJECT");

    private final String roomState;

    private RoomState(String roomState){
        this.roomState = roomState;
    }

}
