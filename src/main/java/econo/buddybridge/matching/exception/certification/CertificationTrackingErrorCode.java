package econo.buddybridge.matching.exception.certification;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CertificationTrackingErrorCode implements ErrorCode {
    REQUEST_COOLDOWN_PERIOD("CT001", HttpStatus.BAD_REQUEST, "봉사 인증 요청을 보냈습니다. 24시간 이후에 다시 보낼 수 있습니다."),
    CERTIFICATION_ALREADY_COMPLETED("CT002", HttpStatus.BAD_REQUEST, "상대방이 봉사 완료 버튼을 눌렀습니다. 인증 폼을 작성해주세요."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    CertificationTrackingErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
