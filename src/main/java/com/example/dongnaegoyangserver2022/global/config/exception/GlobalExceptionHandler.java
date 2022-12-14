package com.example.dongnaegoyangserver2022.global.config.exception;

import com.example.dongnaegoyangserver2022.global.config.exception.error.CommonErrorCode;
import com.example.dongnaegoyangserver2022.global.config.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice //전역적으로 에러를 처리해주는 어노테이션
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /* Spring은 스프링 예외를 미리 처리해둔 ResponseEntityExceptionHandler를 추상 클래스로 제공하고 있다.
    ResponseEntityExceptionHandler에는 스프링 예외에 대한 ExceptionHandler가 모두 구현되어 있으므로
    ControllerAdvice 클래스가 이를 상속받게 하면 된다.
    하지만 에러 메세지는 반환하지 않으므로
    스프링 예외에 대한 에러 응답을 보내려면 handleExceptionInternal(...) 메소드를 오버라이딩(이 아니라 오버로드인 듯?? 인자가 다름) 해야 한다. */

    @ExceptionHandler(RestApiException.class) //RestApiException 발생 시 잡아서 핸들링해라
    public ResponseEntity<Object> handleCustomException(RestApiException e){
        ErrorCode errorCode = e.getErrorCode(); //RestApiException의 errorCode를 가져와서 응답을 만듦
        return handleExceptionInternal(errorCode);
    }

    //이미 스프링에서 정의된 에러도 핸들링. 이 경우 errorCode에 없으니 에러 메세지를 에러 던질 때 보낼수도 있겠지 -> 이걸 따로 얻어다가 전달함
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e){
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

//    //TODO : 이게 될까? 안될듯 url 404도 안됐음..
//    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
//    public ResponseEntity<Object> handleBadRequest(HttpClientErrorException.BadRequest e){
//        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
//        return handleExceptionInternal(errorCode, e.getMessage());
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e){
        return handleExceptionInternal(e.getHttpStatus(), e.getMessage());
    }

    //@Valid 어노테이션으로 발생하는 에러 처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        log.info("JH handleBindException");

        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        HttpStatus httpStatus = errorCode.getHttpStatus();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        //에러 메세지 생성
        StringBuilder builder = new StringBuilder();
        errorList.forEach(error -> {
            String field = ( (FieldError) error).getField(); //필드명 가져오기
            String msg = error.getDefaultMessage(); //기본 에러메세지 가져오기
            builder.append(field).append(" : ").append(msg).append(". ");
        });
        String message = builder.toString();
        builder.setLength(0);

        return handleExceptionInternal(httpStatus, message);
    }


    ///////////-- 메서드들 --//////////////
    public ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode){
        ErrorResponse errorResponse = ErrorResponse.builder() //body로 갈 내용 만듦
                .status(errorCode.getHttpStatus().value()) //httpStatusCode 가져옴
                .errorCode(errorCode.name())
                .message(errorCode.getMessage())
                .build();
        
        return ResponseEntity
                .status(errorCode.getHttpStatus()) //errorCode로부터 httpStatusCode 가져와서 설정
                .body(errorResponse); //errorResonse 를 body로 줌
    }

    //이미 스프링에서 정의된 에러라서 message를 따로 보내줄 때
    public ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .errorCode(errorCode.name())
                .message(message)
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    //CustomException 용
    public ResponseEntity<Object> handleExceptionInternal(HttpStatus httpStatus, String message){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(httpStatus.value())
                .errorCode(httpStatus.getReasonPhrase()) //코드 빼고 설명만 가져옴
                .message(message)
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

}
