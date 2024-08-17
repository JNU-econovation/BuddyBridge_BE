package econo.buddybridge.utils.api;

import econo.buddybridge.common.exception.ErrorResponse;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<B> extends ResponseEntity<B> {

    public ApiResponse(B body, HttpStatus status) {
        super(body, status);
    }

    public ApiResponse(B body, MediaType mediaType, HttpStatus status) {
        super(body, status);
        this.getHeaders().setContentType(mediaType);
    }

    public ApiResponse(B body, HttpHeaders headers, HttpStatus status) {
        super(body, headers, status);
    }

    @Getter
    @AllArgsConstructor
    public static class CustomBody<D> implements Serializable {
        private Boolean success;
        private D data;
        private ErrorResponse error;
    }
}
