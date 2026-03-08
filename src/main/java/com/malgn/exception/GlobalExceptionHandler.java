package com.malgn.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException() {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.METHOD_NOT_ALLOWED);
        return ResponseEntity
                .status(ResponseCode.METHOD_NOT_ALLOWED.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException() {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(ResponseCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> processValidationError(MethodArgumentNotValidException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.BAD_REQUEST,
                e.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionError(
            MethodArgumentTypeMismatchException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.BAD_REQUEST, e);
        return ResponseEntity
                .status(ResponseCode.BAD_REQUEST.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedExceptionError(
            HttpMediaTypeNotSupportedException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.BAD_REQUEST, e);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedExceptionError(
            HttpClientErrorException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> httpServerErrorExceptionError(HttpServerErrorException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(e.getResponseCode());
        return ResponseEntity
                .status(e.getResponseCode().getStatus())
                .body(errorResponse);
    }


}
