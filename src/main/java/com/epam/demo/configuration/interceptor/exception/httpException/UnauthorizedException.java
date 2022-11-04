package com.epam.demo.configuration.interceptor.exception.httpException;

import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException{

    public UnauthorizedException(ExceptionMessageEnum messageEnum){

        super(messageEnum);

        this.httpStatusCode = HttpStatus.UNAUTHORIZED;
    }
}