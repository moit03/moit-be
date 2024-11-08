package com.sparta.moit.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    @Getter
    private final String logMessage;

    public CustomException(ErrorCode errorCode, String logMessage) {
        this.errorCode = errorCode;
        this.logMessage = logMessage;
    }

    public String getKey(){
        return errorCode.getKey();
    }
    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
    public HttpStatus getHttpStatus(){
        return errorCode.getHttpStatus();
    }


}