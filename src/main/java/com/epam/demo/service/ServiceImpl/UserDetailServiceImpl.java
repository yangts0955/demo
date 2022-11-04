package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import com.epam.demo.configuration.interceptor.exception.httpException.NotFoundException;
import com.epam.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        Employee employee = employeeRepository.findByEmail(username)
                .orElseThrow(() -> {throw new NotFoundException(ExceptionMessageEnum.INCORRECT_PASSWORD);
                });
        //TODO 查询对应权限信息


        return new LoginUser(employee);
    }
}
