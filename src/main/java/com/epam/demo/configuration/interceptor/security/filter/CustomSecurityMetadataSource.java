package com.epam.demo.configuration.interceptor.security.filter;

import com.epam.demo.Entity.Resource;
import com.epam.demo.Entity.Role;
import com.epam.demo.repository.ResourceRepository;
import com.epam.demo.service.ResourceService;
import com.epam.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//    @Autowired
//    private ResourceService resourceService;
    @Autowired
    private ResourceRepository resourceRepository;

    AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources){
            if (antPathMatcher.match(resource.getUrl(), requestURI)){
                String[] roles = resource.getRoles().stream()
                        .map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(roles);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
