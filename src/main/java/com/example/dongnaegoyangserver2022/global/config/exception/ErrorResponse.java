package com.example.dongnaegoyangserver2022.global.config.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final String timeStamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//    private final boolean isSuccess = false;
//    private final int status;
    private final int status;
    private final String errorCode;
    private final String message;
}
