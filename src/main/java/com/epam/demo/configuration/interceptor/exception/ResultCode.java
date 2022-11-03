package com.epam.demo.configuration.interceptor.exception;

import org.hibernate.annotations.NotFound;

public enum ResultCode implements IErrorCode{
    SUCCESS(200, "success"),
    FAILED(500, "failed"),
    VALIDATE_FAILED(404, "parameter validate failed"),
    INCORRECT_PASSWORD(401,"incorrect email or password"),
    UNAUTHORIZED(401, "no login or expired token"),
    INCORRECT_TOKEN(403,"incorrect token"),
    FORBIDDEN(403, "forbidden"),

    NOT_FOUND(404, "employee not found"),
    NAME_MODIFY_FORBIDDEN(401, "cannot change name");


    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
