package com.xvanop01.isregatta.base.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HttpExceptionHandler {

    public static ResponseEntity resolve(HttpException exception) {
        HttpReturnCode code = exception.getErrorCode();
        if (code == null) {
            code = HttpReturnCode.BAD_REQUEST;
        }
        return ResponseEntity.status(code.getCode()).contentType(MediaType.TEXT_PLAIN).body(exception.getMessage());
    }
}
