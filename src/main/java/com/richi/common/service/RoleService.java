package com.richi.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richi.common.entity.Role;
import com.richi.common.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
