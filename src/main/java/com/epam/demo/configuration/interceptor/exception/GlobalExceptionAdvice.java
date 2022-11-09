package com.epam.demo.configuration.interceptor.exception;

import com.epam.demo.configuration.interceptor.exception.httpException.HttpException;
import com.epam.demo.configuration.interceptor.exception.httpException.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {

//    @ExceptionHandler(value=Exception.class)
//    @ResponseBody
//    public ResponseEntity<UnifyResponse> handleException(HttpServletRequest req, Exception e) {
//        String requestUrl = req.getRequestURI();
//        String method = req.getMethod();
//        UnifyResponse message = new UnifyResponse(e.getMessage(), method + " " + requestUrl);
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


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
