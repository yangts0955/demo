package com.epam.demo.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.epam.demo.Entity.Role;
import com.epam.demo.repository.RoleRepository;
import com.epam.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void allRoles(){
        Role role = roleRepository.findById(Long.parseLong("1")).get();
        role.getResources().forEach(resource -> {
            System.out.println(resource.getUrl());
        });
    }
}
