package com.example.dongnaegoyangserver2022.global.config.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name(); //TODO : 왜 getName()이 아니지??
    HttpStatus getHttpStatus();
    String getMessage();
}
