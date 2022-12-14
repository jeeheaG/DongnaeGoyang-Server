package com.example.dongnaegoyangserver2022.global.config.exception;

import com.example.dongnaegoyangserver2022.global.config.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ErrorCode errorCode;
}
