package econo.buddybridge.matching.state;

import econo.buddybridge.common.validation.ValueEnum;
import econo.buddybridge.matching.exception.state.MatchingStatusChangeEventInvalidException;
import java.util.Arrays;

public enum MatchingStatusChangeEvent implements ValueEnum<String> {
    TOGGLE_DONE("TOGGLE_DONE"), // PENDING <-> DONE - 매칭 중 or 매칭 완료 클릭 시
    MARK_AS_HELP_NOT_RECEIVED("MARK_AS_HELP_NOT_RECEIVED"), // Done -> FAILED (Only TAKER) - 도움을 받지 못했어요. 클릭 시
    MARK_AS_HELP_RECEIVED("MARK_AS_HELP_RECEIVED"), // DONE -> VOLUNTEERING_COMPLETED (Only TAKER) - 도움을 받았어요! 클릭 시
    SUBMIT_VOLUNTEERING_VERIFICATION("SUBMIT_VOLUNTEERING_VERIFICATION"); // VOLUNTEERING_COMPLETED -> VOLUNTEERING_VERIFIED (Only GIVER) - 봉사 인증 폼 제출 시

    private final String value;

    MatchingStatusChangeEvent(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static MatchingStatusChangeEvent fromValue(String value) {
        return Arrays.stream(MatchingStatusChangeEvent.values())
                .filter(matchingStatusChangeEvent -> matchingStatusChangeEvent.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> MatchingStatusChangeEventInvalidException.EXCEPTION);
    }
}
