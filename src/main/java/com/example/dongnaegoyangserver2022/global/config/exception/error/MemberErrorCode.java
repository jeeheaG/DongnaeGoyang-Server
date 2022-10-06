package com.example.dongnaegoyangserver2022.global.config.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Need authorization"), //401
    GUEST_USER(HttpStatus.UNAUTHORIZED, "Request from guest user. Token is empty."), //401
    NOT_EXIST_KAKAO_MEMBER(HttpStatus.UNAUTHORIZED, "Member connected this kakao id is not exist. Please sign up."), //401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"), //401
    MEMBER_FORBIDDEN(HttpStatus.FORBIDDEN, "Member is forbidden"), //403
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
