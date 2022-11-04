package com.epam.demo.configuration.interceptor.exception;

import com.epam.demo.configuration.interceptor.exception.httpException.HttpException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public ResponseEntity<UnifyResponse> handleException(HttpServletRequest req, Exception e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse message = new UnifyResponse(e.getMessage(), method + " " + requestUrl);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseBody
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handle(HttpServletRequest req, HttpException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
//        ResponseEntity
        UnifyResponse message = new UnifyResponse(e.getMessage(), method + " " + requestUrl);
        return new ResponseEntity<>(message, e.getHttpStatusCode());
    }

}
