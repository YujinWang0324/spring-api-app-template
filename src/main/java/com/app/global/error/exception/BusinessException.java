package com.app.global.error.exception;

import com.app.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    // field 값
    private ErrorCode errorCode;

    // 생성자
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
