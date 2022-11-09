package com.epam.demo.configuration.interceptor.exception.httpException;

import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends HttpException{
    public ObjectNotFoundException(ExceptionMessageEnum messageEnum){

        super(messageEnum);

        this.httpStatusCode = HttpStatus.NOT_FOUND;

    }
}
