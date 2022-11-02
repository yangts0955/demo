package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Employee;
import com.epam.demo.configuration.interceptor.exception.ApiException;
import com.epam.demo.configuration.interceptor.exception.ResultCode;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.repository.EmployeeRepository;
import com.epam.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public String login(Employee employee) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                        employee.getEmail(), employee.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        System.out.println(authentication);
//        if (Objects.isNull(authentication)){
//            throw new ApiException(ResultCode.INCORRECT_PASSWORD);
//        }
        Optional<Employee> existedUser = employeeRepository.findByEmail(employee.getEmail());
        existedUser.orElseThrow(() -> {throw new ApiException(ResultCode.VALIDATE_FAILED);});
        return null;
    }

    @Override
    public void register(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.convertDTO(employeeDto);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodePassword);
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto) {
        Employee existedEmployee = employeeRepository.findByEmail(employeeDto.getEmail())
                .orElseThrow(() -> {throw new ApiException(ResultCode.NOT_FOUND);});
        if (!employeeDto.getName().equals(existedEmployee.getName())){
            throw new ApiException(ResultCode.NAME_MODIFY_FORBIDDEN);
        }
        Employee employee = EmployeeMapper.INSTANCE.convertDtoToEntity(existedEmployee, employeeDto);
        employeeRepository.save(employee);
    }

}
