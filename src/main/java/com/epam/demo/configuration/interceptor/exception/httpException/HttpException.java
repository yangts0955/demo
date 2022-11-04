package com.epam.demo.configuration.interceptor.exception.httpException;


import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    HttpException(ExceptionMessageEnum messageEnum){
        message = messageEnum.getMessage();
    }
    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage(){
        return message;
    }

    protected HttpStatus httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    protected String message = "unknown error";
}
