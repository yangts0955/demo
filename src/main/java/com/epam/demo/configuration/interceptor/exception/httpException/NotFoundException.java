package com.epam.demo.configuration.interceptor.exception.httpException;

import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException{
    public NotFoundException(ExceptionMessageEnum messageEnum){

        super(messageEnum);

        this.httpStatusCode = HttpStatus.NOT_FOUND;

    }
}
