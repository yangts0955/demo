package com.epam.demo.service;

import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.vo.EmployeeVO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeVO> findAllEmployees();

    Boolean updateEmployee(EmployeeDto employee);
}
