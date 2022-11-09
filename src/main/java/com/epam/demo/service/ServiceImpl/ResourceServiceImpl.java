package com.epam.demo.service.ServiceImpl;

import com.epam.demo.Entity.Resource;
import com.epam.demo.Entity.Role;
import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import com.epam.demo.configuration.interceptor.exception.httpException.UnauthorizedException;
import com.epam.demo.repository.ResourceRepository;
import com.epam.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public String[] rolesHasPermission(String requestApi) {
        return new String[0];
    }

//    AntPathMatcher antPathMatcher = new AntPathMatcher();

//    @Override
//    public String[] rolesHasPermission(String requestApi) {

//        Resource role = resourceRepository.findById(Long.parseLong("1")).get();
//        role.getRoles().forEach(resource -> {
//            System.out.println(resource.getName());
//        });
//        return new String[]{};
//    }
}
