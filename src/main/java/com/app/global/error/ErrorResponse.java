package com.app.global.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    public static ErrorResponse of(String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }

    public static ErrorResponse of(String errorCode, BindingResult bindingResult) {
        // BindingResult 에 에러정보들이 들어감
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(createErrorMessage(bindingResult))
                .build();
    }

    // 어떤 필드에 어떤 애러가 있는지 출력하기 위해 createErrorMessage 메소드 생성
    public static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true; // 첫 번째 오류인지 판단

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("] ");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}
