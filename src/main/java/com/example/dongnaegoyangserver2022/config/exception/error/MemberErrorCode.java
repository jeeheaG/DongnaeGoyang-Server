package com.example.dongnaegoyangserver2022.config.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    INACTIVE_MEMBER(HttpStatus.FORBIDDEN, "Member is inactive"), //403
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
