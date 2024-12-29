package econo.buddybridge.matching.state;

public enum MatchingStatusChangeEvent {
    TOGGLE_DONE, // PENDING <-> DONE - 매칭 중 or 매칭 완료 클릭 시
    MARK_AS_HELP_NOT_RECEIVED, // DONE -> FAILED (Only TAKER) - 도움을 받지 못했어요. 클릭 시
    MARK_AS_HELP_RECEIVED, // DDONE -> VOLUNTEERING_COMPLETED (Only TAKER) - 도움을 받았어요! 클릭 시
    SUBMIT_VOLUNTEERING_VERIFICATION // VOLUNTEERING_COMPLETED -> VOLUNTEERING_VERIFIED (Only GIVER) - 봉사 인증 폼 제출 시
}
