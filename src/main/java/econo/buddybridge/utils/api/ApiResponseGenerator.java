package econo.buddybridge.utils.api;

import econo.buddybridge.common.exception.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@UtilityClass
public class ApiResponseGenerator {

    public static ApiResponse<ApiResponse.CustomBody<Void>> success(final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, null, null),status);
    }

    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D data, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, data,null), status);
    }

    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D data, final MediaType mediaType, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, data,null), mediaType,status);
    }

    public static ApiResponse<ApiResponse.CustomBody<ErrorResponse>> fail(final ErrorResponse errorResponse, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(false, null, errorResponse), status);
    }

    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D data, final HttpHeaders headers, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, data,null), headers, status);
    }
}
