package com.richi.richis_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.richi.richis_app.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByLogin(String login);
}
