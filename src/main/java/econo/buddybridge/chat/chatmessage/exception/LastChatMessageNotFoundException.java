package econo.buddybridge.chat.chatmessage.exception;

import econo.buddybridge.common.exception.BusinessException;

public class LastChatMessageNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new LastChatMessageNotFoundException();

    public LastChatMessageNotFoundException() { super(ChatMessageErrorCode.LAST_CHAT_MESSAGE_NOT_FOUND); }
}
