package com.richi.richis_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.richis_app.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
}
