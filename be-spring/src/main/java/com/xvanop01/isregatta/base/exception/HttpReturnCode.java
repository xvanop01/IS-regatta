package com.xvanop01.isregatta.base.exception;

/**
 * HttpReturnCode
 * Podporovane chybove http navratove kody, mozne doplnat
 * @author 2024 Peter Vano
 */
public enum HttpReturnCode {

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
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
