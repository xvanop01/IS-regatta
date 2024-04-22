package com.xvanop01.isregatta.base.exception;

public class HttpException extends Exception {

    private final HttpReturnCode errorCode;

    public HttpException(HttpReturnCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpReturnCode getErrorCode() {
        return this.errorCode;
    }
}
