package com.example.dongnaegoyangserver2022.config.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Need authorization"), //401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"), //401
    MEMBER_FORBIDDEN(HttpStatus.FORBIDDEN, "Member is forbidden"), //403
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
