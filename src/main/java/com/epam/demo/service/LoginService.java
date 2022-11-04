package com.epam.demo.service;

import com.epam.demo.Entity.Employee;
import com.epam.demo.dto.EmployeeDto;

import java.util.Map;

public interface LoginService {

    Map login(Employee employee);

    Boolean register(EmployeeDto employee);

    Boolean logout();
}
