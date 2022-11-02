package com.epam.demo.service;

import com.epam.demo.Entity.Employee;
import com.epam.demo.dto.EmployeeDto;

public interface LoginService {

    String login(Employee employee);

    void register(EmployeeDto employee);

    void updateEmployee(EmployeeDto employee);
}
