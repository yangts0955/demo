package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.Entity.Role;
import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import com.epam.demo.configuration.interceptor.exception.httpException.ObjectNotFoundException;
import com.epam.demo.repository.EmployeeRepository;
import com.sun.jdi.ObjectCollectedException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //查询用户信息
        Employee employee = employeeRepository.findByEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException(ExceptionMessageEnum.INCORRECT_PASSWORD.getMessage());});
        //TODO 查询对应权限信息
        List<String> roles = employee.getRoles().stream().map(Role::getName).toList();
        return new LoginUser(employee, roles);
    }
}
