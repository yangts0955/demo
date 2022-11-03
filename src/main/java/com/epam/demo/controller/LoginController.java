package com.epam.demo.controller;

import com.epam.demo.Entity.DataDictionary;
import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.configuration.interceptor.exception.CommonResult;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("register")
    public CommonResult register(@RequestBody EmployeeDto employee){
        Boolean register = loginService.register(employee);
        return CommonResult.success(register, "注册成功");
    }

    @PostMapping("login")
    public CommonResult<Map<String, String>> login(@RequestBody Employee employee){
        Map<String,String> token = loginService.login(employee);
        return CommonResult.success(token, "登录成功");
    }

    @RequestMapping("logout")
    public CommonResult<Boolean> logout(){
        Boolean logout = loginService.logout();
        return CommonResult.success(logout,"退出登录");
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
