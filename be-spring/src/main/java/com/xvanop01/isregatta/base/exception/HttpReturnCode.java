package com.xvanop01.isregatta.base.exception;

public enum HttpReturnCode {

    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    NOT_IMPLEMENTED(501);

    private final Integer code;

    HttpReturnCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
