package com.xvanop01.isregatta.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HttpExceptionHandler {

    public ResponseEntity resolve(HttpException exception) {
        Http400ReturnCode code = exception.getErrorCode();
        if (code == null) {
            code = Http400ReturnCode.BAD_REQUEST;
        }
        return ResponseEntity.status(code.getCode()).contentType(MediaType.TEXT_PLAIN).body(exception.getMessage());
    }
}
