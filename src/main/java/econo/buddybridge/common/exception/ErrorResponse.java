package econo.buddybridge.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorResponse {

    private final String code;
    private final int status;
    private final String message;

    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationError> invalidParams;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
}
