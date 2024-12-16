package com.richi.richis_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richi.richis_app.repository.RoleRepository;

@Service
public class Roleservice {
    
    @Autowired
    private RoleRepository roleRepository;
}
