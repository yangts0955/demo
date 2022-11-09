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

    public ResponseEntity success(String message) {
        UnifyResponse response = new UnifyResponse(message, request);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}