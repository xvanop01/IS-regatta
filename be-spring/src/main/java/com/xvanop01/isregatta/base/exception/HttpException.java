package com.xvanop01.isregatta.base.exception;

public class HttpException extends Exception {

    private final Http400ReturnCode errorCode;

    public HttpException(Http400ReturnCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public Http400ReturnCode getErrorCode() {
        return this.errorCode;
    }
}
