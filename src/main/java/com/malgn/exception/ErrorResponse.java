package com.malgn.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;

    public static ErrorResponse of(ResponseCode responseCode ) {
        return new ErrorResponse(responseCode.getCode(), responseCode.getMessage());
    }

    public static ErrorResponse of(ResponseCode responseCode, Exception e) {
        return new ErrorResponse(responseCode.getCode(), responseCode.getMessage(e));
    }

    public static ErrorResponse of(ResponseCode code, String message) {
        return new ErrorResponse(code.getCode(), code.getMessage(message));
    }

}
