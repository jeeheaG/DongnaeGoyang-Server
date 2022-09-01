package com.example.dongnaegoyangserver2022.config.exception;

import com.example.dongnaegoyangserver2022.config.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ErrorCode errorCode;
}
