package econo.buddybridge.chat.chatmessage.dto;

import econo.buddybridge.chat.chatmessage.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatMessageReqDto(
        @NotBlank(message = "메시지를 입력해주세요.")
        @Size(max = 300, message = "메시지는 300자 이내로 작성해주세요.")
        String content,

        MessageType messageType
) {

}
