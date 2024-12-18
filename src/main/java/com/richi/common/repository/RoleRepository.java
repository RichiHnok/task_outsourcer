package com.richi.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.common.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByRoleName(String roleName);
}
