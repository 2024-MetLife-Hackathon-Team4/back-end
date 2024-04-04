package com.metlife.hackathon.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final LocalDateTime localDateTime;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    @Builder
    private ErrorResponse(LocalDateTime localDateTime, int status, String error, String message, String path) {
        this.localDateTime = localDateTime;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static ErrorResponse of(final HttpStatus httpStatus, final String message,
                                   final HttpServletRequest httpServletRequest) {
        return ErrorResponse.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(httpServletRequest.getRequestURI())
                .build();
    }
}
