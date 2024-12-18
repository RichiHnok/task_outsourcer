package com.richi.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.common.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByLogin(String login);
}
