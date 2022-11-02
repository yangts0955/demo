package com.epam.demo.controller;

import com.epam.demo.Entity.DataDictionary;
import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("register")
    public void register(@RequestBody EmployeeDto employee){
        loginService.register(employee);
    }

    @PostMapping("login")
    public Boolean login(@RequestBody Employee employee){
        loginService.login(employee);
        return true;
    }

    @GetMapping("logins")
    public String loginGet(){
        return "success";
    }

    @PostMapping("logins")
    public String loginPost(){
        return "success";
    }
}
