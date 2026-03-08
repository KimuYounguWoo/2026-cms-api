package com.malgn.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // Success
    SUCCESS("SUCCESS", HttpStatus.OK, "요청에 성공하였습니다."),

    // Member
    MEMBER_NOT_FOUND("MEM-ERR-001", HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),

    // Content
    CONTENT_NOT_FOUND("CON-ERR-001", HttpStatus.NOT_FOUND, "콘텐츠를 찾을 수 없습니다."),
    NOT_CREATED_USER("CON-ERR-002", HttpStatus.BAD_REQUEST,"작성자가 아닙니다."),

    // Auth
    PASSWORD_INCORRECT("AUT-ERR-001", HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다"),
    DUPLICATE_USERNAME("AUT-ERR-011", HttpStatus.CONFLICT, "중복된 유저네임입니다."),
    ALREADY_LOGGED_IN("AUT-ERR-002", HttpStatus.BAD_REQUEST, "이미 로그인 되어있습니다."),
    NOT_FOUND_USER("AUT-ERR-003", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_REFRESH_TOKEN("AUT-ERR-004", HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
    NOT_FOUND_ACCESS_TOKEN("AUT-ERR-005", HttpStatus.NOT_FOUND, "액세스 토큰을 찾을 수 없습니다."),
    INVALID_TOKEN("AUT-ERR-006", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("AUT-ERR-007", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN("AUT-ERR-008", HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰입니다."),
    INVALID_HEADER_OR_COMPACT_JWT("AUT-ERR-009", HttpStatus.UNAUTHORIZED, "헤더 또는 컴팩트 JWT가 잘못되었습니다."),
    UNAUTHORIZED("AUT-ERR-010", HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    FORBIDDEN("AUT-ERR-011", HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // Global
    BAD_REQUEST("GLB-ERR-001", HttpStatus.NOT_FOUND, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED("GLB-ERR-002", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR("GLB-ERR-003", HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;


    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
