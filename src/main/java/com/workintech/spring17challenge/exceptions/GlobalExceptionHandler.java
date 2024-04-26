package com.workintech.spring17challenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e) {
        log.error("API exception Occurred, Exception details: {}", e.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(e.getHttpStatus().value(),e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("API exception Occurred, Exception details: {}", e.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
