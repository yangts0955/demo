package com.epam.demo.controller;

import com.epam.demo.service.EmployeeService;
import com.epam.demo.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("employees")
    public List<EmployeeVO> findAllEmployees(){
        return employeeService.findAllEmployees();
    }

}
