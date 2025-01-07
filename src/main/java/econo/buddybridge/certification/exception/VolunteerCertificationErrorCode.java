package econo.buddybridge.certification.exception;

import econo.buddybridge.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum VolunteerCertificationErrorCode implements ErrorCode {
    VOLUNTEER_CERTIFICATION_NOT_ALLOWED_MATCHING_STATUS("VC001", HttpStatus.BAD_REQUEST, "봉사 인증 폼 작성 중 문제가 발생했습니다. 봉사 인증은 매칭 상태가 봉사 완료(VOLUNTEERING_COMPLETED, VOLUNTEERING_VERIFIED)인 경우에만 가능합니다."),
    VOLUNTEER_CERTIFICATION_VOLUNTEER_MISMATCH("VC002", HttpStatus.BAD_REQUEST, "봉사 인증 폼 작성 중 문제가 발생했습니다. 봉사자가 일치하지 않습니다. 관리자에게 문의해주세요."),
    VOLUNTEER_CERTIFICATION_POST_TYPE_MISMATCH("VC003", HttpStatus.BAD_REQUEST, "봉사 인증 폼 작성 중 문제가 발생했습니다. 게시글 유형이 일치하지 않습니다. 관리자에게 문의해주세요."),
    VOLUNTEER_CERTIFICATION_SCHEDULE_DATE_MISMATCH("VC004", HttpStatus.BAD_REQUEST, "봉사 인증 폼 작성 중 문제가 발생했습니다. 봉사 일자가 게시글에 명시된 일자와 일치하지 않습니다."),
    VOLUNTEER_CERTIFICATION_ASSISTANCE_TIME_MISMATCH("VC005", HttpStatus.BAD_REQUEST, "봉사 인증 폼 작성 중 문제가 발생했습니다. 봉사 시간이 게시글에 명시된 시간과 일치하지 않습니다."),
    VOLUNTEER_CERTIFICATION_ASSISTANCE_TYPE_MISMATCH("VC006", HttpStatus.BAD_REQUEST, "봉사 인증 폼 작성 중 문제가 발생했습니다. 도움 유형이 게시글에 명시된 유형과 일치하지 않습니다."),
    VOLUNTEER_CERTIFICATION_MATCHING_MISMATCH("VC007", HttpStatus.BAD_REQUEST, "봉사 인증 폼 수정 중 문제가 발생했습니다. 매칭 정보가 일치하지 않습니다."),
    VOLUNTEER_CERTIFICATION_NOT_FOUND("VC008", HttpStatus.NOT_FOUND, "봉사 인증 폼을 찾을 수 없습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    VolunteerCertificationErrorCode(String code, HttpStatus httpStatus, String message) {
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
