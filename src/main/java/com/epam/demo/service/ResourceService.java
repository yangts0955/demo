package com.epam.demo.service;

import com.epam.demo.Entity.Role;

import java.util.Set;

public interface ResourceService {

    String[] rolesHasPermission(String requestApi);
}
