package com.example.dongnaegoyangserver2022.controller;

import com.example.dongnaegoyangserver2022.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.config.exception.error.CommonErrorCode;
import com.example.dongnaegoyangserver2022.config.exception.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

//    @GetMapping("/v1/test/error/rest/1")
//    public ResponseEntity<Object> restApiException1(){
//        throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
//    }

    @GetMapping("/v1/test/error/rest/2")
    public ResponseEntity<Object> restApiException2(){
        throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @GetMapping("/v1/test/error/rest/3")
    public ResponseEntity<Object> restApiException3(){
        throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

//    @GetMapping("/v1/test/error/rest/4")
//    public ResponseEntity<Object> restApiException4(){
//        throw new RestApiException(MemberErrorCode.INACTIVE_MEMBER);
//    }

    @GetMapping("/v1/test/error/illegal")
    public ResponseEntity<Object> illegalException(){
        throw new IllegalArgumentException("illegal message");
    }

}
