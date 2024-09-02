package com.richi.richis_app.service.user_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.richis_app.entity.User;
import com.richi.richis_app.repository.user_rep.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
        
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }
    
    
}
