package com.epam.demo.controller;

import com.epam.demo.Entity.Employee;
import com.epam.demo.configuration.interceptor.exception.UnifyResponse;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody EmployeeDto employee){
        loginService.register(employee);
        return new ResponseEntity<>( "register successfully", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Employee employee){
        Map<String, String> token = loginService.login(employee);
        return new ResponseEntity<>( token, HttpStatus.OK);
    }

}
