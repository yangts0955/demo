package com.epam.demo.configuration.interceptor.exception.httpException;

import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException{

    public ForbiddenException (ExceptionMessageEnum messageEnum){

        super(messageEnum);

        this.httpStatusCode = HttpStatus.FORBIDDEN;
    }
}
