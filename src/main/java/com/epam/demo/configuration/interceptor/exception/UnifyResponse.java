package com.epam.demo.configuration.interceptor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class UnifyResponse {

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }

    private String message;
    private String request;

    public UnifyResponse(String message, String request){
        this.message = message;
        this.request = request;
    }

    public static ResponseEntity success(String message, HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse response = new UnifyResponse(message, method+ " " + url);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}