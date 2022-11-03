package com.epam.demo.controller;

import com.epam.demo.Entity.Employee;
import com.epam.demo.configuration.interceptor.exception.CommonResult;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginApiController {
    @Autowired
    private LoginService loginService;

    @PutMapping("update")
    public CommonResult updateEmployee(@RequestBody EmployeeDto employeeDto){
        Boolean update = loginService.updateEmployee(employeeDto);
        return CommonResult.success(update, "更新成功");
    }
}
