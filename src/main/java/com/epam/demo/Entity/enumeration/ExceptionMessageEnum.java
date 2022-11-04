package com.epam.demo.Entity.enumeration;

public enum ExceptionMessageEnum {

    SUCCESS( "success"),
    FAILED( "failed"),
    VALIDATE_FAILED( "parameter validate failed"),
    INCORRECT_PASSWORD("incorrect email or password"),
    UNAUTHORIZED( "no login or expired token"),
    FORBIDDEN( "forbidden"),
    INCORRECT_TOKEN("incorrect token"),
    NAME_MODIFY_FORBIDDEN("cannot change name or email"),
    EMAIL_EXISTED("email existed");

    private final String message;
    ExceptionMessageEnum(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
