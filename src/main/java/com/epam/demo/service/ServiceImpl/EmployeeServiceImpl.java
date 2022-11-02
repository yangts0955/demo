package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Employee;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.repository.EmployeeRepository;
import com.epam.demo.service.EmployeeService;
import com.epam.demo.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeVO> findAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeVO> employeeVOs = employees.stream().map(EmployeeMapper.INSTANCE::convertToVO).toList();
        return employeeVOs;
    }
}
