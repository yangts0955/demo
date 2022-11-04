package com.epam.demo.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import com.epam.demo.configuration.interceptor.exception.httpException.ForbiddenException;
import com.epam.demo.configuration.interceptor.exception.httpException.UnauthorizedException;
import com.epam.demo.configuration.interceptor.exception.httpException.ValidateException;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.mapper.EmployeeMapper;
import com.epam.demo.repository.EmployeeRepository;
import com.epam.demo.service.LoginService;
import com.epam.demo.utils.JwtUtil;
import com.epam.demo.utils.RedisCache;
import com.epam.demo.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
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
            throw new ForbiddenException(ExceptionMessageEnum.INCORRECT_PASSWORD);
        }
        return authentication;
    }

    private String makeToken(Authentication authentication){
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getEmployee().getId().toString();
        String json = LoginUserParseJson(loginUser);
        String token = JwtUtil.createJWT(json);
        //authenticate存入redis
        redisCache.setCacheObject(REDIS_CACHE_KEY_HEAD+userId,token);
        return token;
    }

    private String parseJson(LoginUser loginUser){
        EmployeeVO employeeVO = EmployeeMapper.INSTANCE.convertToVO(loginUser.getEmployee());
        return JSON.toJSONString(employeeVO);
    }

    private String LoginUserParseJson(LoginUser loginUser){
        return JSON.toJSONString(loginUser);
    }

    @Override
    public Boolean register(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.convertDTO(employeeDto);
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent())
            throw new ValidateException(ExceptionMessageEnum.EMAIL_EXISTED);
        employee.setPassword(encodePassword(employee.getPassword()));
        employeeRepository.save(employee);     // SQL error!!! how to catch?
        return true;
    }

    @Override
    public Boolean logout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication.getPrincipal().toString());  //获取不到，不知道怎么测试 TODO: try catch
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            Long userid = loginUser.getEmployee().getId();
//        Long userid = LocalEmployee.getEmployee().getId();
            redisCache.deleteObject(REDIS_CACHE_KEY_HEAD+userid); // 从jwt 得到id
        }catch (Exception e){
            throw new UnauthorizedException(ExceptionMessageEnum.FORBIDDEN);
        }
        return true;
    }

    private String encodePassword(String plaintext){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plaintext);
    }

}
