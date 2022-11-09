package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import com.epam.demo.configuration.interceptor.exception.httpException.ObjectNotFoundException;
import com.epam.demo.configuration.interceptor.exception.httpException.ValidateException;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.repository.EmployeeRepository;
import com.epam.demo.service.EmployeeService;
import com.epam.demo.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Override
    public Boolean updateEmployee(EmployeeDto employeeDto) {
        Employee existedEmployee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> {throw new ObjectNotFoundException(ExceptionMessageEnum.EMPLOYEE_NOT_FOUND);});
        isModified(existedEmployee.getName(), employeeDto.getName());
        isModified(existedEmployee.getEmail(),employeeDto.getEmail());
        employeeDto.setPassword(encodePassword(employeeDto.getPassword()));
        Employee employee = EmployeeMapper.INSTANCE.convertDtoToEntity(existedEmployee, employeeDto);
        employeeRepository.save(employee);
        return true;
    }

    private Boolean isModified(String before, String after){
        if (after == null || !after.equals(before)){
            throw new ValidateException(ExceptionMessageEnum.NAME_MODIFY_FORBIDDEN);
        }
        return true;
    }

    private String encodePassword(String plaintext){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plaintext);
    }
}
