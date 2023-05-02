package com.xvanop01.isregatta.base.exception;

public enum Http400ReturnCode {

    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409);

    private final Integer code;

    Http400ReturnCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
