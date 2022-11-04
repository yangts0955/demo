package com.epam.demo.configuration.interceptor.exception.httpException;

import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;

public class ValidateException extends HttpException{

    public ValidateException(ExceptionMessageEnum messageEnum){

        super(messageEnum);

        this.httpStatusCode = HttpStatus.CONFLICT;
    }
}
