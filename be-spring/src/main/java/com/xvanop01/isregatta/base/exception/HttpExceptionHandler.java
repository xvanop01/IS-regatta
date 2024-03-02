package com.xvanop01.isregatta.base.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HttpExceptionHandler {

    public static ResponseEntity resolve(HttpException exception) {
        Http400ReturnCode code = exception.getErrorCode();
        if (code == null) {
            code = Http400ReturnCode.BAD_REQUEST;
        }
        return ResponseEntity.status(code.getCode()).contentType(MediaType.TEXT_PLAIN).body(exception.getMessage());
    }
}
