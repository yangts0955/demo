package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.configuration.interceptor.exception.ApiException;
import com.epam.demo.configuration.interceptor.exception.ResultCode;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.repository.EmployeeRepository;
import com.epam.demo.service.LoginService;
import com.epam.demo.utils.JwtUtil;
import com.epam.demo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    private static final String REDIS_CACHE_KEY_HEAD = "login:";

    private static final String TOKEN_STR = "token";

    @Override
    public Map<String, String> login(Employee employee) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                        employee.getEmail(), employee.getPassword());
        Authentication authentication = isAuthenticated(authenticationToken);
        //使用userid生成token, authentication存入redis
        String jwt = makeToken(authentication);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put(TOKEN_STR,jwt);
        return map;
    }

    private Authentication isAuthenticated(UsernamePasswordAuthenticationToken token){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        }catch (BadCredentialsException e){
            throw new ApiException(ResultCode.INCORRECT_PASSWORD);
        }
        return authentication;
    }

    private String makeToken(Authentication authentication){
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getEmployee().getId().toString();
        //authenticate存入redis
        redisCache.setCacheObject(REDIS_CACHE_KEY_HEAD+userId,loginUser);
        System.out.println((LoginUser) redisCache.getCacheObject(REDIS_CACHE_KEY_HEAD+userId));
        return JwtUtil.createJWT(userId);
    }

    @Override
    public Boolean register(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.convertDTO(employeeDto);
        employee.setPassword(encodePassword(employee.getPassword()));
        employeeRepository.save(employee);     // SQL error!!! how to catch?
        return true;
    }

    @Override
    public Boolean updateEmployee(EmployeeDto employeeDto) {
        Employee existedEmployee = employeeRepository.findByEmail(employeeDto.getEmail())
                .orElseThrow(() -> {throw new ApiException(ResultCode.NOT_FOUND);}); // if modified email stored in database
        isNameModified(existedEmployee.getName(), employeeDto.getName());
        employeeDto.setPassword(encodePassword(employeeDto.getPassword()));
        Employee employee = EmployeeMapper.INSTANCE.convertDtoToEntity(existedEmployee, employeeDto);
        employeeRepository.save(employee);
        return true;
    }

    private Boolean isNameModified(String before, String after){
        if (after == null || !after.equals(before)){
            throw new ApiException(ResultCode.NAME_MODIFY_FORBIDDEN);
        }
        return true;
    }

    @Override
    public Boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication.getPrincipal().toString());  //获取不到，不知道怎么测试 TODO: try catch
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getEmployee().getId();
        redisCache.deleteObject(REDIS_CACHE_KEY_HEAD+userid);
        return true;
    }

    private String encodePassword(String plaintext){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plaintext);
    }

}
