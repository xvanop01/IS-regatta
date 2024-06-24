package com.xvanop01.isregatta.base.exception;

/**
 * HttpException
 * Vlastna vynimka pre manazovanie chybovych odpovedi pre FE
 * @author 2024 Peter Vano
 */
public class HttpException extends Exception {

    /**
     * Http kod, ktory sa ma pouzit pre odpved
     */
    private final HttpReturnCode errorCode;

    public HttpException(HttpReturnCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpReturnCode getErrorCode() {
        return this.errorCode;
    }
}
