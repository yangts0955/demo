package com.epam.demo.controller;

import com.epam.demo.configuration.interceptor.exception.UnifyResponse;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.service.EmployeeService;
import com.epam.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class LoginApiController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;

    @PutMapping("update")
    public ResponseEntity updateEmployee(@RequestBody EmployeeDto employeeDto, HttpServletRequest request){
        employeeService.updateEmployee(employeeDto);
        return new ResponseEntity<>( "update successfully", HttpStatus.OK);
    }

    @RequestMapping("logout")
    public ResponseEntity logout(HttpServletRequest request){
        loginService.logout();
        return new ResponseEntity<>( "logout successfully", HttpStatus.OK);
    }
}
